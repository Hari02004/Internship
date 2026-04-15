package com.student.demo;

import com.student.demo.entity.Student;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class ResumeService {
    private final ProcessResume parser;

    public ResumeService(ProcessResume parser) {
        this.parser = parser;
    }

    public Student processResume(MultipartFile file){
        if(file.isEmpty())
            throw new RuntimeException("File is empty");
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
            return serviceStudent;
        }catch(IOException ex){
            ex.printStackTrace();
            throw new RuntimeException("File Upload error in ResumeService"+" "+ex.getMessage());
        }

    }
}
