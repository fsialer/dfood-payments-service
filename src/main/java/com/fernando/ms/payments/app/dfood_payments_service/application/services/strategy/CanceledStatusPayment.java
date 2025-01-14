package com.fernando.ms.payments.app.dfood_payments_service.application.services.strategy;

import com.fernando.ms.payments.app.dfood_payments_service.domain.exception.StatusPaymentRulesException;
import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Payment;
import com.fernando.ms.payments.app.dfood_payments_service.domain.models.StatusPayment;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CanceledStatusPayment implements IStatusPaymentStrategy{
    @Override
    public String doOperation(Payment payment) {
        List<String> status=payment.getStatusPayments().stream().map(StatusPayment::getStatus).toList();

        if(status.contains("REFUND") ||status.contains("ACCEPTED") ){
            throw new StatusPaymentRulesException("Payment cannot cancel.");
        }
        return "CANCELED";
    }

    @Override
    public boolean isApplicable(String statusPayment) {
        return "CANCELED".equalsIgnoreCase(statusPayment);
    }
}
