package com.fernando.ms.payments.app.dfood_payments_service.application.ports.output;

import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentPersistencePort {
    List<Payment> findAll();
    Optional<Payment> findById(Long id);
}
