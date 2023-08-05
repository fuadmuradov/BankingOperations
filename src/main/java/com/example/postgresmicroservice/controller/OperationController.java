package com.example.postgresmicroservice.controller;

import com.example.postgresmicroservice.dto.response.TransactionResponse;
import com.example.postgresmicroservice.service.implService.OperationService;
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

    @PostMapping("/topup/{customerId}")
    public ResponseEntity<TransactionResponse> TopUp(@PathVariable Long customerId, @RequestParam BigDecimal amount){
        return ResponseEntity.status(HttpStatus.OK)
                .body(operationService.topUpBalance(customerId,amount));
    }
    @PostMapping("/purchase/{customerId}")
    public ResponseEntity<TransactionResponse> Purchase(@PathVariable Long customerId, @RequestParam BigDecimal amount){
        return ResponseEntity.status(HttpStatus.OK)
                .body(operationService.purchase(customerId, amount));
    }

    @PostMapping("/refund/{transactionId}")
    public ResponseEntity<TransactionResponse> Refund(@PathVariable("transactionId") Long transactionId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(operationService.refund(transactionId));
    }

    @GetMapping("/get-transaction/{customerId}")
    public ResponseEntity<List<TransactionResponse>> getTransactionbyUserId(@PathVariable Long customerId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(operationService.getTransactionsByCustomerId(customerId));
    }
}
