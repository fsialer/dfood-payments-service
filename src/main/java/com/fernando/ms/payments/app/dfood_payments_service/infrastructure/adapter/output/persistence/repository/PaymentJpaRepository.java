package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence.repository;

import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence.models.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity,Long> {
}
