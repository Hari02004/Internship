package com.student.demo;

import com.student.demo.entity.Student;
import com.student.demo.exception.ResponseDto;
import com.student.demo.exception.CustomRunTimeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class ResumeService {
    private final ProcessResume parser;
    private final StudentRepository studentRepository;

    public ResumeService(ProcessResume parser, StudentRepository studentRepository) {
        this.parser = parser;
        this.studentRepository = studentRepository;
    }

    public ResponseDto processResume(MultipartFile file){
        if(file.isEmpty())
            throw new CustomRunTimeException(HttpStatus.FORBIDDEN,"File is empty");
        try{
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File folder = new File(uploadDir);
            if(!folder.exists()) folder.mkdirs();
            String fileName = System.currentTimeMillis()+"_"+file.getOriginalFilename();
            File savedFile = new File(folder,fileName);
            file.transferTo(savedFile);
            String content = Files.readString(savedFile.toPath());
            String extractName = parser.extractName(content);
            String extractEmail = parser.extractEmail(content);
            Student serviceStudent = new Student();
            serviceStudent.setFileName(extractName);
            serviceStudent.setFileEmail(extractEmail);
            serviceStudent.setPath(savedFile.getAbsolutePath());
            studentRepository.save(serviceStudent);
            ResponseDto responseDto = new ResponseDto();
            responseDto.setCourse(serviceStudent.getCourse());
            responseDto.setFileName(serviceStudent.getFileName());
            responseDto.setName(serviceStudent.getName());

            return responseDto;
        }catch(IOException ex){
            ex.printStackTrace();
            throw new CustomRunTimeException(HttpStatus.BAD_REQUEST,ex.getMessage());
        }

    }
}
