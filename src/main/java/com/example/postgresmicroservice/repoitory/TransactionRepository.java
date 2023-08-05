package com.example.postgresmicroservice.repoitory;

import com.example.postgresmicroservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
