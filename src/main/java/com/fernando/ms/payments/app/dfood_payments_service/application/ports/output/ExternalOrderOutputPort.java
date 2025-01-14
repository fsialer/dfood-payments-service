package com.fernando.ms.payments.app.dfood_payments_service.application.ports.output;

import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Order;

public interface ExternalOrderOutputPort {
    Order findBydId(Long id);
}
