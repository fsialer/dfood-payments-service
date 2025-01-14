package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.ms.payments.app.dfood_payments_service.application.ports.input.PaymentInputPort;
import com.fernando.ms.payments.app.dfood_payments_service.domain.exception.PaymentNotFoundException;
import com.fernando.ms.payments.app.dfood_payments_service.domain.exception.StatusPaymentRulesException;
import com.fernando.ms.payments.app.dfood_payments_service.domain.exception.StatusPaymentStrategyException;
import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Payment;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.mapper.PaymentRestMapper;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.models.request.CreatePaymentRequest;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.models.response.ErrorResponse;
import com.fernando.ms.payments.app.dfood_payments_service.utils.TestUtilsPayment;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;

import static com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.models.enums.ErrorType.FUNCTIONAL;
import static com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.models.enums.ErrorType.SYSTEM;
import static com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.utils.ErrorCatalog.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {PaymentRestAdapter.class})
public class GlobalControllerAdviceTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PaymentInputPort paymentInputPort;

    @MockBean
    private PaymentRestMapper paymentRestMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Expect OrderNotFoundException When Order Identifier Is Unknown")
    void Expect_OrderNotFoundException_When_OrderIdentifierIsUnknown() throws Exception {
        when(paymentInputPort.findById(anyLong()))
                .thenThrow(new PaymentNotFoundException());
        mockMvc.perform(get("/payments/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result->{
                    ErrorResponse errorResponse=objectMapper.readValue(
                            result.getResponse().getContentAsString(), ErrorResponse.class);
                    assertAll(
                            ()->assertEquals(PAYMENT_NOT_FOUND.getCode(),errorResponse.getCode()),
                            ()->assertEquals(FUNCTIONAL,errorResponse.getType()),
                            ()->assertEquals(PAYMENT_NOT_FOUND.getMessage(),errorResponse.getMessage()),
                            ()->assertNotNull(errorResponse.getTimestamp())
                    );
                });

    }

    @Test
    void Expect_RuntimeException_When_OrderInformationIsInvalid() throws Exception {
        when(paymentInputPort.findAll())
                .thenThrow(new RuntimeException("Generic error"));
        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(result->{
                    ErrorResponse errorResponse=objectMapper.readValue(
                            result.getResponse().getContentAsString(), ErrorResponse.class
                    );
                    assertAll(
                            ()->assertEquals(INTERNAL_SERVER_ERROR.getCode(),errorResponse.getCode()),
                            ()->assertEquals(SYSTEM,errorResponse.getType()),
                            ()->assertEquals(INTERNAL_SERVER_ERROR.getMessage(),errorResponse.getMessage()),
                            ()->assertNotNull(errorResponse.getDetails()),
                            ()->assertNotNull(errorResponse.getTimestamp())
                    );
                });
    }

    @Test
    @DisplayName("Expect MethodArgumentNotValidException When PaymentInformationIsInvalid")
    void Expect_MethodArgumentNotValidException_When_ShipmentInformationIsInvalid() throws Exception {

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{}"))

                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    ErrorResponse errorResponse = objectMapper.readValue(
                            result.getResponse().getContentAsString(), ErrorResponse.class);
                    assertAll(
                            () -> assertEquals(PAYMENT_BAD_PARAMETERS.getCode(), errorResponse.getCode()),
                            () -> assertEquals(FUNCTIONAL, errorResponse.getType()),
                            () -> assertEquals(PAYMENT_BAD_PARAMETERS.getMessage(), errorResponse.getMessage()),
                            () -> assertNotNull(errorResponse.getTimestamp())
                    );
                });
    }

    @Test
    @DisplayName("Expect StatusPaymentStrategyException When Status Of Payment Is Invalid")
    void Expect_StatusPaymentStrategyException_WhenStatusOfPaymentIsInvalid() throws Exception {
        CreatePaymentRequest rq= TestUtilsPayment.buildCreatePaymentRequestMock();
        Payment payment=TestUtilsPayment.buildPaymentMock();
        when(paymentRestMapper.toPayment(any(CreatePaymentRequest.class)))
                .thenReturn(payment);
        when(paymentInputPort.save(any(Payment.class)))
                .thenThrow(new StatusPaymentStrategyException("Status payment not found: "+payment.getStatusPayment()));
        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rq)))

                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    ErrorResponse errorResponse = objectMapper.readValue(
                            result.getResponse().getContentAsString(), ErrorResponse.class);
                    assertAll(
                            () -> assertEquals(STATUS_PAYMENT_STRATEGY_ERROR.getCode(), errorResponse.getCode()),
                            () -> assertEquals(FUNCTIONAL, errorResponse.getType()),
                            () -> assertEquals(STATUS_PAYMENT_STRATEGY_ERROR.getMessage(), errorResponse.getMessage()),
                            () -> assertNotNull(errorResponse.getTimestamp())
                    );
                });
    }

    @Test
    @DisplayName("Expect StatusPaymentRulesException Whe PaymentStatus Is Incorrect")
    void Expect_StatusPaymentRulesException_When_PaymentStatusIsIncorrect() throws Exception {
        CreatePaymentRequest rq= TestUtilsPayment.buildCreatePaymentRequestMock();
        Payment payment=TestUtilsPayment.buildPaymentMock();
        when(paymentRestMapper.toPayment(any(CreatePaymentRequest.class)))
                .thenReturn(payment);
        when(paymentInputPort.save(any(Payment.class)))
                .thenThrow(new StatusPaymentRulesException("Payment never was processing"));

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rq)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    ErrorResponse errorResponse = objectMapper.readValue(
                            result.getResponse().getContentAsString(), ErrorResponse.class);
                    assertAll(
                            () -> assertEquals(STATUS_PAYMENT_RULES_ERROR.getCode(), errorResponse.getCode()),
                            () -> assertEquals(FUNCTIONAL, errorResponse.getType()),
                            () -> assertEquals(STATUS_PAYMENT_RULES_ERROR.getMessage(), errorResponse.getMessage()),
                            () -> assertNotNull(errorResponse.getTimestamp())
                    );
                });
    }

    @Test
    @DisplayName("Expect FeignException When Orders Client Is Invalid")
    void Expect_FeignException_When_OrdersClientIsInvalid() throws Exception {
        CreatePaymentRequest rq= TestUtilsPayment.buildCreatePaymentRequestMock();
        Payment payment=TestUtilsPayment.buildPaymentMock();
        when(paymentRestMapper.toPayment(any(CreatePaymentRequest.class)))
                .thenReturn(payment);
        when(paymentInputPort.save(any(Payment.class)))
                .thenThrow(new StatusPaymentRulesException("Payment never was processing"));

        FeignException feignException = FeignException.errorStatus(
                "GET /orders/"+1L,
                feign.Response.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .reason("Order not found")
                        .request(feign.Request.create(Request.HttpMethod.GET, "/orders/1", Collections.emptyMap(), null, null, null))
                        .build()
        );

        when(paymentInputPort.save(any(Payment.class)))
                .thenThrow(feignException);

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rq)))
                .andExpect(result -> {
                    ErrorResponse errorResponse = objectMapper.readValue(
                            result.getResponse().getContentAsString(), ErrorResponse.class);
                    assertAll(
                            () -> assertEquals(WEB_CLIENT_ERROR.getCode(), errorResponse.getCode()),
                            () -> assertEquals(FUNCTIONAL, errorResponse.getType()),
                            () -> assertEquals(WEB_CLIENT_ERROR.getMessage(), errorResponse.getMessage()),
                            () -> assertNotNull(errorResponse.getTimestamp())
                    );
                });
    }

}
