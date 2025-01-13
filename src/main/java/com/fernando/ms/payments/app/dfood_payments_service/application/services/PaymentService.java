package com.fernando.ms.payments.app.dfood_payments_service.application.services;

import com.fernando.ms.payments.app.dfood_payments_service.application.ports.input.PaymentInputPort;
import com.fernando.ms.payments.app.dfood_payments_service.application.ports.output.PaymentPersistencePort;
import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentService implements PaymentInputPort {
    private final PaymentPersistencePort paymentPersistencePort;
    @Override
    public List<Payment> findAll() {
        return paymentPersistencePort.findAll();
    }
}
