package com.example.postgresmicroservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public static ResponseEntity<?> handleBaseException(BaseException baseException){
        baseException.printStackTrace();
        return ResponseEntity.status(baseException.getCode()).body(baseException.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public static ResponseEntity<?> handleException(Exception exception){
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }
}
