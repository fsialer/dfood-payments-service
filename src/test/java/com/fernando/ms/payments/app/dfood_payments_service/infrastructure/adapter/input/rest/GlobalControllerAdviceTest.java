package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.ms.payments.app.dfood_payments_service.application.ports.input.PaymentInputPort;
import com.fernando.ms.payments.app.dfood_payments_service.domain.exception.PaymentNotFoundException;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.mapper.PaymentRestMapper;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.models.response.ErrorResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.models.enums.ErrorType.FUNCTIONAL;
import static com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.models.enums.ErrorType.SYSTEM;
import static com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.utils.ErrorCatalog.INTERNAL_SERVER_ERROR;
import static com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.utils.ErrorCatalog.PAYMENT_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
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

}
