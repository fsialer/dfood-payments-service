package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence.mapper;

import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Payment;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence.models.PaymentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentPersistenceMapper {
    List<Payment> toPayments(List<PaymentEntity> payments);
}
