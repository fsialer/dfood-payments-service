package com.fernando.ms.payments.app.dfood_payments_service.application.services;

import com.fernando.ms.payments.app.dfood_payments_service.application.ports.input.PaymentInputPort;
import com.fernando.ms.payments.app.dfood_payments_service.application.ports.output.ExternalCustomerOutputPort;
import com.fernando.ms.payments.app.dfood_payments_service.application.ports.output.ExternalOrderOutputPort;
import com.fernando.ms.payments.app.dfood_payments_service.application.ports.output.PaymentPersistencePort;
import com.fernando.ms.payments.app.dfood_payments_service.application.services.strategy.IStatusPaymentStrategy;
import com.fernando.ms.payments.app.dfood_payments_service.domain.exception.PaymentNotFoundException;
import com.fernando.ms.payments.app.dfood_payments_service.domain.exception.StatusPaymentStrategyException;
import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentService implements PaymentInputPort {
    private final PaymentPersistencePort paymentPersistencePort;
    private final List<IStatusPaymentStrategy> statusPaymentStrategyList;
    private final ExternalOrderOutputPort externalOrderOutputPort;
    private final ExternalCustomerOutputPort externalCustomerOutputPort;

    @Override
    public List<Payment> findAll() {
        return paymentPersistencePort.findAll();
    }

    @Override
    public Payment findById(Long id) {
        return paymentPersistencePort.findById(id).orElseThrow(PaymentNotFoundException::new);
    }

    @Override
    public Payment save(Payment payment) {
        payment.setOrder(externalOrderOutputPort.findBydId(payment.getOrder().getId()));
        externalCustomerOutputPort.verifyExistsById(payment.getCustomer().getId());
        IStatusPaymentStrategy statusPaymentStrategy=statusPaymentStrategyList.stream()
                .filter(strategy->strategy.isApplicable("PROCESSING"))
                .findFirst()
                .orElseThrow(()->new StatusPaymentStrategyException("Status payment not found: "+payment.getStatusPayment()));
        payment.setStatusPayment(statusPaymentStrategy.doOperation(payment));
        return paymentPersistencePort.save(payment);
    }
}
