package com.example.postgresmicroservice.mapper;

import com.example.postgresmicroservice.dto.request.CustomerRequest;
import com.example.postgresmicroservice.dto.response.CustomerResponse;
import com.example.postgresmicroservice.entity.Customer;

public class CustomerMapper {
    public static Customer toEntity(CustomerRequest customerRequest){
        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setSurname(customerRequest.getSurname());
        customer.setBirthDate(customerRequest.getBirthDate());
        customer.setGsmNumber(customerRequest.getGsmNumber());
        return customer;
    }

    public static CustomerResponse toResponse(Customer customer){
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(customer.getId());
        customerResponse.setName(customer.getName());
        customerResponse.setSurname(customer.getSurname());
        customerResponse.setBirthDate(customer.getBirthDate());
        customerResponse.setGsmNumber(customer.getGsmNumber());
        customerResponse.setBalance(customer.getBalance());
        return customerResponse;
    }
}
