package com.example.postgresmicroservice.service.implService;

import com.example.postgresmicroservice.dto.response.CustomerResponse;
import com.example.postgresmicroservice.dto.response.TransactionResponse;
import com.example.postgresmicroservice.entity.Customer;
import com.example.postgresmicroservice.entity.Transaction;
import com.example.postgresmicroservice.enums.ErrorEnum;
import com.example.postgresmicroservice.enums.TransactionTypeEnum;
import com.example.postgresmicroservice.exception.BaseException;
import com.example.postgresmicroservice.mapper.CustomerMapper;
import com.example.postgresmicroservice.mapper.TransactionMapper;
import com.example.postgresmicroservice.repoitory.CustomerRepository;
import com.example.postgresmicroservice.repoitory.TransactionRepository;
import com.example.postgresmicroservice.service.iService.IOperationService;
import jakarta.transaction.Transactional;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperationService implements IOperationService {
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public OperationService(CustomerRepository customerRepository, TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }



    @Override
    public List<TransactionResponse> getTransactionsByCustomerId(Long customerId) {
        List<TransactionResponse> transactions = transactionRepository
                .findAll()
                .stream()
                .filter(tr -> tr.getCustomer().getId() == customerId)
                .map(tr -> new TransactionResponse(tr.getId(), tr.getCustomer().getId(), tr.getType(), tr.getAmount(), tr.getTransactionDate()))
                .collect(Collectors.toList());

        return transactions;
    }

    @Override
    @Transactional
    public TransactionResponse topUpBalance(Long customerId, BigDecimal amount) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> BaseException.of(ErrorEnum.CUSTOMER_NOT_FOUND));

        customer.setBalance(customer.getBalance().add(amount));

        CustomerResponse customerDb = CustomerMapper.toResponse(customerRepository.save(customer));

        return TransactionMapper
                .toResponse(transactionRepository
                        .save(TransactionMapper.getTransactioForDB(customer, amount, TransactionTypeEnum.TOPUP, false)));
    }

    @Override
    @Transactional
    public TransactionResponse purchase(Long customerId, BigDecimal amount) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                BaseException.of(ErrorEnum.CUSTOMER_NOT_FOUND));

        if (customer.getBalance().compareTo(amount) >= 0) {
            customer.setBalance(customer.getBalance().subtract(amount));

            customerRepository.save(customer);

            return TransactionMapper
                    .toResponse(transactionRepository
                            .save(TransactionMapper.getTransactioForDB(customer, amount, TransactionTypeEnum.PURCHASE, false)));

        } else {
            throw BaseException.of(ErrorEnum.BALANCE_NOT_ENOUGH);
        }
    }

    @Override
    @Transactional
    public TransactionResponse refund(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> BaseException.of(ErrorEnum.CUSTOMER_NOT_FOUND));

        Customer customer = customerRepository.findById(transaction.getCustomer().getId())
                .orElseThrow(() -> BaseException.of(ErrorEnum.TRANSACTION_NOT_FOUND));

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
                    .save(TransactionMapper.getTransactioForDB(customer, transaction.getAmount()
                            .multiply(BigDecimal.valueOf(0.5)).setScale(2), TransactionTypeEnum.REFUND, false));

            return TransactionMapper
                    .toResponse(transactionSaved);

        } else {
            throw BaseException.of(ErrorEnum.ONLY_PURCHASE);
        }
    }

}
