package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.mapper;

import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Payment;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.models.response.PaymentResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentRestMapper {
    List<PaymentResponse> toPaymentsResponse(List<Payment> payments);

    PaymentResponse toPaymentResponse(Payment payment);
}
