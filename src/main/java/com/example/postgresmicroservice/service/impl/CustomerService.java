package com.example.postgresmicroservice.service.impl;

import com.example.postgresmicroservice.dto.request.CustomerRequest;
import com.example.postgresmicroservice.dto.response.CustomerResponse;
import com.example.postgresmicroservice.entity.Customer;
import com.example.postgresmicroservice.enums.ErrorEnum;
import com.example.postgresmicroservice.exception.BaseException;
import com.example.postgresmicroservice.mapper.CustomerMapper;
import com.example.postgresmicroservice.repoitory.CustomerRepository;
import com.example.postgresmicroservice.service.iService.ICustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;

    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        return CustomerMapper.toResponse(customerRepository.save(CustomerMapper.toEntity(customerRequest)));
    }

    @Override
    public List<CustomerResponse> getAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(cs-> new CustomerResponse(cs.getId(),
                cs.getName(),
                cs.getSurname(),
                cs.getBirthDate(),
                cs.getGsmNumber(),
                cs.getBalance())).collect(Collectors.toList());
    }

    @Override
    public CustomerResponse getCustomerById(Long customerId) {
        return CustomerMapper.toResponse(customerRepository.findById(customerId)
                .orElseThrow(()-> BaseException.of(ErrorEnum.CUSTOMER_NOT_FOUND)));
    }

}
