package com.fernando.ms.payments.app.dfood_payments_service.application.services.strategy;

import com.fernando.ms.payments.app.dfood_payments_service.domain.exception.StatusPaymentRulesException;
import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Payment;
import com.fernando.ms.payments.app.dfood_payments_service.domain.models.StatusPayment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProcessingStatusPayment implements IStatusPaymentStrategy{
    @Override
    public String doOperation(Payment payment) {

        return "PROCESSING";
    }

    @Override
    public boolean isApplicable(String statusPayment) {
        return "PROCESSING".equalsIgnoreCase(statusPayment);
    }
}
