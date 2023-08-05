package com.example.postgresmicroservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse implements Serializable {

    private Long id;

    private String name;

    private String surname;

    private Date birthDate;

    private String gsmNumber;

    private BigDecimal balance;

    //private List<TransactionResponse>

}
