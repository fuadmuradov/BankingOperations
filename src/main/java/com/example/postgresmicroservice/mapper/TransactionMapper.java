package com.example.postgresmicroservice.mapper;

import com.example.postgresmicroservice.dto.response.TransactionResponse;
import com.example.postgresmicroservice.entity.Customer;
import com.example.postgresmicroservice.entity.Transaction;
import com.example.postgresmicroservice.enums.TransactionTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TransactionMapper implements Serializable {
    public static TransactionResponse toResponse(Transaction transaction){
        TransactionResponse trResponse = new TransactionResponse();
        trResponse.setId(transaction.getId());
        trResponse.setAmount(transaction.getAmount());
        trResponse.setType(transaction.getType());
        trResponse.setCustomerId(transaction.getCustomer().getId());
        trResponse.setTransactionDate(transaction.getTransactionDate());
        return trResponse;
    }

    public static Transaction getTransactioForDB(Customer customer, BigDecimal amount, TransactionTypeEnum type, boolean isRefunded) {
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(new Date());
        transaction.setAmount(amount);
        transaction.setCustomer(customer);
        transaction.setType(type);
        transaction.setIsRefunded(isRefunded);
        return transaction;
    }
}
