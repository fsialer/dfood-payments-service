package com.fernando.ms.payments.app.dfood_payments_service.domain.models;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

    private Long id;
    private LocalDateTime datePayment;
    private Double amount;
    private String typePayment;
    private String methodPayment;
    private String statusPayment;
}
