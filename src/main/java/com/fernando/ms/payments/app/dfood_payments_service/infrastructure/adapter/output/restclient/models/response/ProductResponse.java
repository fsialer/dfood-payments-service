package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.restclient.models.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private Double amount;
}
