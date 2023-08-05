package com.example.postgresmicroservice.controller;
import com.example.postgresmicroservice.dto.request.CustomerRequest;
import com.example.postgresmicroservice.dto.response.CustomerResponse;
import com.example.postgresmicroservice.service.implService.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(request));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomer(){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getAllCustomer());
    }

    @GetMapping("/get-customer/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Long customerId){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerById(customerId));
    }

}

