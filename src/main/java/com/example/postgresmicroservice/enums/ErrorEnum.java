package com.example.postgresmicroservice.enums;

import lombok.Getter;

@Getter
public enum ErrorEnum {
    TRANSACTION_NOT_FOUND("Transaction not found",404 ),
    CUSTOMER_NOT_FOUND("Customer not found", 404),
    BALANCE_NOT_ENOUGH("Balance not enough",400),
    ONLY_PURCHASE("Only purchase transactions can be refunded.", 400),
    TRANSACTION_ALREADY_REFUNDED("Transaction already refunded.", 400);

    private final String message;
    private final Integer code;

    ErrorEnum(String message, Integer code) {
        this.message = message;
        this.code = code;
    }
}
