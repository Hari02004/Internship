package com.student.demo;

import com.student.demo.DTO.AuthResponseDTO;
import com.student.demo.entity.RegisterDetails;
import com.student.demo.entity.Student;
import com.student.demo.exception.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class Controller {

    private final StudentService studentService;
    private final ResumeService resumeService;
    private final ServiceLogin serviceLogin;
    public Controller(StudentService studentService, ResumeService resumeService,ServiceLogin serviceLogin ) {
        this.studentService = studentService;
        this.resumeService = resumeService;
        this.serviceLogin = serviceLogin;
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
    public ResponseEntity<ResponseDto> getDetailsFromTheFile(@RequestParam("file")MultipartFile file){
        ResponseDto allTheDetails = resumeService.processResume(file);
        return ResponseEntity.ok(allTheDetails);
    }
    @PostMapping({"/login"})
    public ResponseEntity<AuthResponseDTO> login(@RequestBody RegisterDetails loginInfo){
        AuthResponseDTO response = new AuthResponseDTO();
        String token = serviceLogin.verifyLogin(loginInfo);
        response.setToken(token);
        response.setMessage("Login successfull");
        return ResponseEntity.ok(response);
    }
    @PostMapping({"/register"})
    public ResponseEntity<String> register(@RequestBody RegisterDetails info){
        String response = serviceLogin.registerUser(info);
        return ResponseEntity.ok(response);
    }
}
