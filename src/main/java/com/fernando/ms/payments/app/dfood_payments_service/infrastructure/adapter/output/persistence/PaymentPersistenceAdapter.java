package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence;

import com.fernando.ms.payments.app.dfood_payments_service.application.ports.output.PaymentPersistencePort;
import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Payment;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence.mapper.PaymentPersistenceMapper;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence.repository.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class PaymentPersistenceAdapter implements PaymentPersistencePort {
    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentPersistenceMapper paymentPersistenceMapper;
    @Override
    public List<Payment> findAll() {
        return paymentPersistenceMapper.toPayments(paymentJpaRepository.findAll());
    }
}
