package com.student.demo.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(CustomRunTimeException.class)
    public ResponseEntity<?> handleException(CustomRunTimeException ex){
        return ResponseEntity
                .status(ex.getStatus())
                .body(Map.of("message",ex.getMessage(),"status",ex.getStatus()));
    }
}
