package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "status_payment")
public class StatusPaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    @ManyToOne
    //@JoinColumn(name = "payment_id", nullable = false)
    private PaymentEntity payment;
    private LocalDateTime createdAt;

}
