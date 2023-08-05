package com.example.postgresmicroservice.dto.response;

import com.example.postgresmicroservice.entity.Customer;
import com.example.postgresmicroservice.enums.TransactionTypeEnum;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse implements Serializable {

    private Long id;

    private Long customerId;

    private TransactionTypeEnum type;

    private BigDecimal amount;

    private LocalDateTime transactionDate;


}
