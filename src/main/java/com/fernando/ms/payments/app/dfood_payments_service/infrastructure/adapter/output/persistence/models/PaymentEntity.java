package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private String statusPayment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private PaymentOrder paymentOrder;

    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private PaymentCustomer paymentCustomer;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StatusPaymentEntity> statusPaymentEntityList;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void addStatusPayment(){
        if (this.statusPaymentEntityList == null) {
            this.statusPaymentEntityList = new ArrayList<>();
        }
        this.statusPaymentEntityList.add(StatusPaymentEntity
                .builder()
                .status(this.getStatusPayment())
                .createdAt(LocalDateTime.now())
                .payment(this)
                .build());
    }

    public void setPaymentCustomerId(Long customerId){
        this.paymentCustomer = PaymentCustomer
                .builder()
                .customerId(customerId)
                .payment(this)
                .build();
    }

    public void setPaymentOrderId(Long orderId){
        this.paymentOrder = PaymentOrder
                .builder()
                .orderId(orderId)
                .payment(this)
                .build();
    }
}
