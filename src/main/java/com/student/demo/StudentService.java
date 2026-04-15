package com.student.demo;

import com.student.demo.entity.Student;
import com.student.demo.exception.RunTimeException;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repository;
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }
    public List<Student> getAllUser(){
        return repository.findAll();
    }
    public Student getStudentById(Long id){
        return repository.findById(id).orElseThrow(() -> new RunTimeException(HttpStatus.NOT_FOUND, "Student not found"));
    }
    public Student saveUser(Student student){
        return repository.save(student);
    }
    public void deleteUserById(Long id){
        repository.deleteById(id);
    }
    public Student getStudentAndUpdateById(Student student,Long id){
        Student stu_List = getStudentById(id);
        System.out.println(stu_List);
        if(stu_List.getName()!=null){
            stu_List.setName(student.getName());
        }
        if(stu_List.getCourse()!=null){
            stu_List.setCourse(student.getCourse());
        }
        return repository.save(stu_List);
    }
}
