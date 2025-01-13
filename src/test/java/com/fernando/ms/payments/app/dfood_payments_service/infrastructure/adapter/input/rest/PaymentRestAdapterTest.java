package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.ms.payments.app.dfood_payments_service.application.ports.input.PaymentInputPort;
import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Payment;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.mapper.PaymentRestMapper;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.models.response.PaymentResponse;
import com.fernando.ms.payments.app.dfood_payments_service.utils.TestUtilsPayment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {PaymentRestAdapter.class})
public class PaymentRestAdapterTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PaymentInputPort paymentInputPort;

    @MockBean
    private PaymentRestMapper paymentRestMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("When Payments Are Availability Expect Payments Information Successfully")
    void When_PaymentsAreAvailability_Expect_PaymentsInformationSuccessfully() throws Exception {

        Payment payment = TestUtilsPayment.buildPaymentMock();
        PaymentResponse paymentResponse= TestUtilsPayment.buildPaymentResponseMock();

        when(paymentInputPort.findAll())
                .thenReturn(Collections.singletonList(payment));

        when(paymentRestMapper.toPaymentsResponse(anyList()))
                .thenReturn(Collections.singletonList(paymentResponse));

        mockMvc.perform(get("/payments").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(1))
                .andDo(print());

        Mockito.verify(paymentInputPort,times(1)).findAll();
        Mockito.verify(paymentRestMapper,times(1)).toPaymentsResponse(anyList());
    }

    @Test
    @DisplayName("When Payment Identifier Is Valid Expect Payment Information Successfully")
    void When_PaymentIdentifierIsValid_Expect_PaymentInformationSuccessfully() throws Exception {

        when(paymentInputPort.findById(anyLong()))
                .thenReturn(TestUtilsPayment.buildPaymentMock());

        when(paymentRestMapper.toPaymentResponse(any(Payment.class)))
                .thenReturn(TestUtilsPayment.buildPaymentResponseMock());

        mockMvc.perform(get("/payments/{id}",1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andDo(print());

        Mockito.verify(paymentInputPort,times(1)).findById(anyLong());
        Mockito.verify(paymentRestMapper,times(1)).toPaymentResponse(any(Payment.class));
    }
}
