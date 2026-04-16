package com.student.demo.exception;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class ResponseDto {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String course;
    private String fileName;
    private String fileEmail;
}
