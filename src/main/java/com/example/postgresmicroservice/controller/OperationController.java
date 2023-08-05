package com.example.postgresmicroservice.controller;

import com.example.postgresmicroservice.dto.response.TransactionResponse;
import com.example.postgresmicroservice.service.impl.OperationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/operations")
public class OperationController {

    private final OperationService operationService;



    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @PostMapping("/topup")
    public ResponseEntity<TransactionResponse> TopUp
            (@RequestHeader Long customerId, @RequestHeader BigDecimal amount)
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(operationService.topUpBalance(customerId,amount));
    }
    @PostMapping("/purchase")
    public ResponseEntity<TransactionResponse> Purchase(@RequestHeader Long customerId, @RequestHeader BigDecimal amount){
        return ResponseEntity.status(HttpStatus.OK)
                .body(operationService.purchase(customerId, amount));
    }

    @PostMapping("/refund")
    public ResponseEntity<TransactionResponse> Refund(@RequestHeader Long transactionId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(operationService.refund(transactionId));
    }

    @GetMapping("/get-transaction")
    public ResponseEntity<List<TransactionResponse>> getTransactionbyUserId(@RequestHeader Long customerId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(operationService.getTransactionsByCustomerId(customerId));
    }
}
