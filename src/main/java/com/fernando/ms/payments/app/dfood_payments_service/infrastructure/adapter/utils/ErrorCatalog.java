package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCatalog {
    PAYMENT_NOT_FOUND("PAYMENT_MS_001", "Payment not found."),
    PAYMENT_BAD_PARAMETERS("PAYMENT_MS_002", "Invalid parameters for creation payment"),
    STATUS_PAYMENT_STRATEGY_ERROR("PAYMENT_MS_003","Status type selected is invalid."),
    STATUS_PAYMENT_RULES_ERROR("PAYMENT_MS_004","Status payment rule not allowed."),
    WEB_CLIENT_ERROR("PAYMENT_MS_005", "Error with services."),
    INTERNAL_SERVER_ERROR("PAYMENT_MS_000", "Internal server error.");


    private final String code;
    private final String message;
}
