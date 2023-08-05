package com.example.postgresmicroservice.repoitory;

import com.example.postgresmicroservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

