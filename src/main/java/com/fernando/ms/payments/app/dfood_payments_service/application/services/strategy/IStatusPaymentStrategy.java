package com.fernando.ms.payments.app.dfood_payments_service.application.services.strategy;

import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Payment;

public interface IStatusPaymentStrategy {
    String doOperation(Payment payment);
    boolean isApplicable(String statusPayment);
}
