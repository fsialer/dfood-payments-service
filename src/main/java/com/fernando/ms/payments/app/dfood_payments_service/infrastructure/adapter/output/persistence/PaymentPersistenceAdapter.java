package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence;

import com.fernando.ms.payments.app.dfood_payments_service.application.ports.output.PaymentPersistencePort;
import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Payment;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence.mapper.PaymentPersistenceMapper;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence.models.PaymentEntity;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence.repository.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class PaymentPersistenceAdapter implements PaymentPersistencePort {
    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentPersistenceMapper paymentPersistenceMapper;
    @Override
    public List<Payment> findAll() {
        return paymentPersistenceMapper.toPayments(paymentJpaRepository.findAll());
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return paymentJpaRepository.findById(id).map(paymentPersistenceMapper::toPayment);
    }

    @Override
    public Payment save(Payment payment) {
        PaymentEntity paymentEntity=paymentPersistenceMapper.toPaymentEntity(payment);
        paymentEntity.addStatusPayment();
        paymentEntity.setPaymentCustomerId(payment.getCustomer().getId());
        paymentEntity.setPaymentOrderId(payment.getOrder().getId());
        return paymentPersistenceMapper.toPayment(paymentJpaRepository.save(paymentEntity));
    }
}
