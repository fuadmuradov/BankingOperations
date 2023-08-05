package com.example.postgresmicroservice.exception;

import com.example.postgresmicroservice.enums.ErrorEnum;
import lombok.Data;

@Data
public class BaseException extends RuntimeException{
    private String message;

    private  Integer code;

    public static BaseException of(ErrorEnum errorEnum){
        BaseException exception = new BaseException();
        exception.setCode(errorEnum.getCode());
        exception.setMessage(errorEnum.getMessage());
        return exception;
    }
}
