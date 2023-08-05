package com.example.postgresmicroservice.mapper;

import com.example.postgresmicroservice.dto.request.CustomerRequest;
import com.example.postgresmicroservice.dto.response.CustomerResponse;
import com.example.postgresmicroservice.entity.Customer;
import lombok.Builder;

import java.math.BigDecimal;


public class CustomerMapper {
    public static Customer toEntity(CustomerRequest customerRequest){
        return Customer.builder()
                .name(customerRequest.getName())
                .surname(customerRequest.getSurname())
                .birthDate(customerRequest.getBirthDate())
                .gsmNumber(customerRequest.getGsmNumber())
                .balance(BigDecimal.valueOf(100))
                .build();
    }

    public static CustomerResponse toResponse(Customer customer){
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .birthDate(customer.getBirthDate())
                .gsmNumber(customer.getGsmNumber())
                .balance(customer.getBalance())
                .build();
    }
}
