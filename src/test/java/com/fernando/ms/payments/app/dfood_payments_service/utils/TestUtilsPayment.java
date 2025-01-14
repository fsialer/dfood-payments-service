package com.fernando.ms.payments.app.dfood_payments_service.utils;

import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Customer;
import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Order;
import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Payment;
import com.fernando.ms.payments.app.dfood_payments_service.domain.models.StatusPayment;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.models.request.CreatePaymentRequest;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.models.response.PaymentResponse;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence.models.PaymentCustomer;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence.models.PaymentEntity;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence.models.PaymentOrder;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence.models.StatusPaymentEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestUtilsPayment {
    public static Payment buildPaymentMock(){
        return Payment.builder()
                .id(1L)
                .datePayment(LocalDateTime.now())
                .amount(459.33)
                .statusPayment("PROCESSING")
                .customer(Customer.builder().id(1L).build())
                .order(Order.builder().id(1L).build())
                .statusPayments(new ArrayList<>(List.of(StatusPayment.builder().id(1L).status("PROCESSING").build())))
                .build();
    }

    public static PaymentEntity buildPaymentEntityMock(){
        return PaymentEntity.builder()
                .id(1L)
                .datePayment(LocalDateTime.now())
                .amount(459.33)
                .statusPayment("PROCESSING")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .paymentCustomer(PaymentCustomer.builder().customerId(1L).build())
                .paymentOrder(PaymentOrder.builder().orderId(1L).build())
                .statusPaymentEntityList(new ArrayList<>(List.of(StatusPaymentEntity.builder().id(1L).status("PROCESSING").createdAt(LocalDateTime.now()).build())))
                .build();
    }

    public static PaymentResponse buildPaymentResponseMock(){
        return PaymentResponse.builder()
                .id(1L)
                .datePayment(LocalDateTime.now())
                .amount(459.33)
                .statusPayment("REGISTERED")
                .build();
    }

    public static CreatePaymentRequest buildCreatePaymentRequestMock(){
        return CreatePaymentRequest.builder()
                .datePayment(LocalDateTime.now())
                .amount(459.33)
                .customerId(1L)
                .orderId(1L)
                .build();
    }


}
