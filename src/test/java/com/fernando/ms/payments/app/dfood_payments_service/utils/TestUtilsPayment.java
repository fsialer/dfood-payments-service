package com.fernando.ms.payments.app.dfood_payments_service.utils;

import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Payment;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.models.response.PaymentResponse;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence.models.PaymentEntity;

import java.time.LocalDateTime;

public class TestUtilsPayment {
    public static Payment buildPaymentMock(){
        return Payment.builder()
                .id(1L)
                .datePayment(LocalDateTime.now())
                .amount(459.33)
                .methodPayment("CARD_DEBIT")
                .typePayment("ACCOUNT")
                .statusPayment("REGISTERED")
                .build();
    }

    public static PaymentEntity buildPaymentEntityMock(){
        return PaymentEntity.builder()
                .id(1L)
                .datePayment(LocalDateTime.now())
                .amount(459.33)
                .methodPayment("CARD_DEBIT")
                .typePayment("ACCOUNT")
                .statusPayment("REGISTERED")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static PaymentResponse buildPaymentResponseMock(){
        return PaymentResponse.builder()
                .id(1L)
                .datePayment(LocalDateTime.now())
                .amount(459.33)
                .methodPayment("CARD_DEBIT")
                .typePayment("ACCOUNT")
                .statusPayment("REGISTERED")
                .build();
    }
}
