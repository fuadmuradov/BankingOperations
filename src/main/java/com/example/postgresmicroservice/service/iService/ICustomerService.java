package com.example.postgresmicroservice.service.iService;

import com.example.postgresmicroservice.dto.request.CustomerRequest;
import com.example.postgresmicroservice.dto.response.CustomerResponse;
import com.example.postgresmicroservice.entity.Customer;

import java.math.BigDecimal;
import java.util.List;

public interface ICustomerService {
    CustomerResponse createCustomer(CustomerRequest customerRequest);
    List<CustomerResponse> getAllCustomer();
    CustomerResponse getCustomerById(Long customerId);
}
