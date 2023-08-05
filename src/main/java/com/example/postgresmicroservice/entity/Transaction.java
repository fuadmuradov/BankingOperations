package com.example.postgresmicroservice.entity;

import com.example.postgresmicroservice.enums.TransactionTypeEnum;
import jakarta.persistence.*;
import lombok.Data;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class Transaction {
    @Id
    @SequenceGenerator(name = "id_generator",initialValue = 100000, allocationSize = 1)
    @GeneratedValue(generator = "id_generator",strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @Enumerated
    private TransactionTypeEnum type;
    private BigDecimal amount;
    private Date transactionDate;
    private Boolean isRefunded;


}
