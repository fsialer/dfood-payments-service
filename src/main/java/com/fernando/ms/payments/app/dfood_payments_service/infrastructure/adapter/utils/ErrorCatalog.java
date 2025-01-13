package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCatalog {
    PAYMENT_NOT_FOUND("PAYMENT_MS_001", "Payment not found."),
    INTERNAL_SERVER_ERROR("PAYMENT_MS_000", "Internal server error.");


    private final String code;
    private final String message;
}
