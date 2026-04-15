package com.student.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class RunTimeException extends RuntimeException {
    private final HttpStatus status;
    public RunTimeException(HttpStatus status, String message){
        super(message);
        this.status = status;
    }
}
