package com.example.postgresmicroservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Customer {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private String surname;
        @Column(name = "birth_date")
        private Date birthDate;
        @Column(name = "gsm_number")
        private String gsmNumber;
        private BigDecimal balance = BigDecimal.valueOf(100);

        @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
        private List<Transaction> transactions;
}


