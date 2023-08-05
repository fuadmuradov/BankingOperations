package com.example.postgresmicroservice.enums;

public enum TransactionTypeEnum {
    TOPUP("TOPUP"),
    PURCHASE("PURCHASE"),
    REFUND("REFUND");

    private String label;

    TransactionTypeEnum(String label) {
        this.label = label;
    }

}
