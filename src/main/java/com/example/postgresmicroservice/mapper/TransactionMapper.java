package com.example.postgresmicroservice.mapper;

import com.example.postgresmicroservice.dto.response.TransactionResponse;
import com.example.postgresmicroservice.entity.Customer;
import com.example.postgresmicroservice.entity.Transaction;
import com.example.postgresmicroservice.enums.TransactionTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionMapper {
    public static TransactionResponse toResponse(Transaction transaction){
        return TransactionResponse.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .customerId(transaction.getCustomer().getId())
                .transactionDate(transaction.getTransactionDate())
                .build();
    }


}
