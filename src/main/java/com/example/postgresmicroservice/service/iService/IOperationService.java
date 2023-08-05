package com.example.postgresmicroservice.service.iService;

import com.example.postgresmicroservice.dto.response.CustomerResponse;
import com.example.postgresmicroservice.dto.response.TransactionResponse;

import java.math.BigDecimal;
import java.util.List;

public interface IOperationService {
    List<TransactionResponse> getTransactionsByCustomerId (Long customerId);
    TransactionResponse topUpBalance (Long customerId, BigDecimal amount);
    TransactionResponse purchase (Long customerId, BigDecimal amount);
    TransactionResponse refund (Long transactionId);


}
