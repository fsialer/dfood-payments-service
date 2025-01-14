package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.mapper;

import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Customer;
import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Order;
import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Payment;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.models.request.CreatePaymentRequest;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.models.response.PaymentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentRestMapper {
    List<PaymentResponse> toPaymentsResponse(List<Payment> payments);

    PaymentResponse toPaymentResponse(Payment payment);

    @Mapping(target = "order", expression = "java(mapOrder(rq))")
    @Mapping(target = "customer", expression = "java(mapCustomer(rq))")
    @Mapping(target = "datePayment", expression = "java(mapDatePayment(rq))")
    Payment toPayment(CreatePaymentRequest rq);

    default Order mapOrder(CreatePaymentRequest rq){
        return Order.builder().id(rq.getOrderId()).build();
    }

    default Customer mapCustomer(CreatePaymentRequest rq){
        return Customer.builder().id(rq.getCustomerId()).build();
    }

    default LocalDateTime mapDatePayment(CreatePaymentRequest rq){
        return rq.getDatePayment()==null?LocalDateTime.now():rq.getDatePayment();
    }
}
