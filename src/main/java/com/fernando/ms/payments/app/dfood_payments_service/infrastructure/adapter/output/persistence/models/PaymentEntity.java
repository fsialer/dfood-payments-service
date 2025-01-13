package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "payments")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime datePayment;
    private Double amount;
    private String typePayment;
    private String methodPayment;
    private String statusPayment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
