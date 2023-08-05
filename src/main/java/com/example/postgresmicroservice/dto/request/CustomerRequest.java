package com.example.postgresmicroservice.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class CustomerRequest implements Serializable {
    private String name;
    private String surname;
    private Date birthDate;
    private String gsmNumber;
}
