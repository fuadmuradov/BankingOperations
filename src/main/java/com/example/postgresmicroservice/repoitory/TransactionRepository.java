package com.example.postgresmicroservice.repoitory;

import com.example.postgresmicroservice.entity.Transaction;
import com.example.postgresmicroservice.enums.TransactionTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
