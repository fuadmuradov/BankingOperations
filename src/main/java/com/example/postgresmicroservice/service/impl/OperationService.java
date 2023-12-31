package com.example.postgresmicroservice.service.impl;
import com.example.postgresmicroservice.dto.response.TransactionResponse;
import com.example.postgresmicroservice.entity.Customer;
import com.example.postgresmicroservice.entity.Transaction;
import com.example.postgresmicroservice.enums.ErrorEnum;
import com.example.postgresmicroservice.enums.TransactionTypeEnum;
import com.example.postgresmicroservice.exception.BaseException;
import com.example.postgresmicroservice.mapper.TransactionMapper;
import com.example.postgresmicroservice.repoitory.CustomerRepository;
import com.example.postgresmicroservice.repoitory.TransactionRepository;
import com.example.postgresmicroservice.service.iService.IOperationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class OperationService implements IOperationService {

    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public List<TransactionResponse> getTransactionsByCustomerId(Long customerId) {
        List<TransactionResponse> transactions = transactionRepository.findAll()
                .stream()
                .filter(tr -> tr.getCustomer().getId().equals(customerId))
                .map(TransactionMapper::toResponse)
                .collect(Collectors.toList());

        return transactions;
    }

    @Override
    @Transactional
    public TransactionResponse topUpBalance(Long customerId, BigDecimal amount) {
        log.info(" Top-up request customerId:{} and amount:{}", customerId, amount);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> BaseException.of(ErrorEnum.CUSTOMER_NOT_FOUND));

        customer.setBalance(customer.getBalance().add(amount));

        customerRepository.save(customer);

        Transaction transaction = transactionRepository
                .save(createTransactionDto(customer, amount, TransactionTypeEnum.TOPUP, false));

        log.info("Top-up Transaction object {}", transaction);
        return TransactionMapper.toResponse(transaction);
    }

    @Override
    @Transactional
    public TransactionResponse purchase(Long customerId, BigDecimal amount) {
        log.info(" Top-up request customerId:{} and amount:{}", customerId, amount);
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                BaseException.of(ErrorEnum.CUSTOMER_NOT_FOUND));

        if (customer.getBalance().compareTo(amount) >= 0) {
            customer.setBalance(customer.getBalance().subtract(amount));

            customerRepository.save(customer);

            Transaction transaction = transactionRepository
                    .save(createTransactionDto(customer, amount, TransactionTypeEnum.PURCHASE, false));

            log.info("Purchase Transaction: {}", transaction);
            return TransactionMapper
                    .toResponse(transaction);

        } else {
            throw BaseException.of(ErrorEnum.BALANCE_NOT_ENOUGH);
        }
    }

    @Override
    @Transactional
    public TransactionResponse refund(Long transactionId) {

        log.info("Refund request transactionId:{}", transactionId);

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> BaseException.of(ErrorEnum.TRANSACTION_NOT_FOUND));

        Customer customer = customerRepository.findById(transaction.getCustomer().getId())
                .orElseThrow(() -> BaseException.of(ErrorEnum.CUSTOMER_NOT_FOUND));

        if (transaction.getIsRefunded()) {
           throw  BaseException.of(ErrorEnum.TRANSACTION_ALREADY_REFUNDED);
        }
        if (transaction.getType().equals(TransactionTypeEnum.PURCHASE)) {

            customer.setBalance(customer.getBalance()
                    .add(transaction.getAmount()
                            .multiply(BigDecimal.valueOf(0.5)).setScale(2)));
            customerRepository.save(customer);

            transaction.setIsRefunded(true);
            transactionRepository.save(transaction);

            Transaction transactionSaved = transactionRepository
                    .save(createTransactionDto(customer, transaction.getAmount()
                            .multiply(BigDecimal.valueOf(0.5)).setScale(2), TransactionTypeEnum.REFUND, false));
            log.info("Refunded Transaction: {}", transactionSaved);

            return TransactionMapper
                    .toResponse(transactionSaved);

        } else {
            throw BaseException.of(ErrorEnum.ONLY_PURCHASE);
        }
    }
    private static Transaction createTransactionDto(Customer customer, BigDecimal amount, TransactionTypeEnum type, boolean isRefunded) {
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAmount(amount);
        transaction.setCustomer(customer);
        transaction.setType(type);
        transaction.setIsRefunded(isRefunded);
        return transaction;
    }

}
