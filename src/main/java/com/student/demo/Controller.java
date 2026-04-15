package com.student.demo;

import com.student.demo.entity.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    private final StudentService studentService;
    private final ResumeService resumeService;
    public Controller(StudentService studentService, ResumeService resumeService ) {
        this.studentService = studentService;
        this.resumeService = resumeService;
    }
        @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        return ResponseEntity.ok(studentService.getAllUser());
    }
    @PostMapping
    public ResponseEntity<Student> postStudents(@RequestBody Student student){
        Student studentList = studentService.saveUser(student);
        return ResponseEntity.ok(studentList);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){
        studentService.deleteUserById(id);
        return new ResponseEntity<>("User is Deleted Successfully", HttpStatus.OK);
    }
    @GetMapping("/student/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id){
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateById(@PathVariable Long id,@RequestBody Student student){
        Student updateStudent = studentService.getStudentAndUpdateById(student,id);
        return ResponseEntity.ok(updateStudent);
    }
    @PostMapping({"/uploads"})
    public ResponseEntity<Student> getDetailsFromTheFile(@RequestParam("file")MultipartFile file){
        Student allTheDetails = resumeService.processResume(file);
        return ResponseEntity.ok(allTheDetails);
    }
}
