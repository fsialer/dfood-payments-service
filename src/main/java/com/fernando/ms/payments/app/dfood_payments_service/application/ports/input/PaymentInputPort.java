package com.fernando.ms.payments.app.dfood_payments_service.application.ports.input;

import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Payment;

import java.util.List;

public interface PaymentInputPort {
    List<Payment> findAll();
    Payment findById(Long id);
}
