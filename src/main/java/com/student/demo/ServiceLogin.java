package com.student.demo;

import com.student.demo.entity.RegisterDetails;
import com.student.demo.exception.CustomRunTimeException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceLogin {
    private final RepositoryForLogin repositoryForLogin;
    private final BCryptPasswordEncoder passwordEncoder;
    public ServiceLogin(RepositoryForLogin repositoryForLogin, BCryptPasswordEncoder passwordEncoder){
        this.repositoryForLogin=repositoryForLogin;
        this.passwordEncoder = passwordEncoder;
    }
    public String verifyLogin(RegisterDetails email){
        RegisterDetails findEmail = repositoryForLogin.findByEmail(email.getEmail())
                .orElseThrow(() -> new CustomRunTimeException(HttpStatus.NOT_FOUND,"User not found"));
        if(!passwordEncoder.matches(email.getPassword(), (findEmail.getPassword())))
            return "Invalid password";
        JwtToken token = new JwtToken();
        return token.generateToken(findEmail.getEmail());
    }
    public String registerUser(RegisterDetails details){
        if(repositoryForLogin.findByEmail(details.getEmail()).isPresent()){
                return "User already existed";
            }
        details.setPassword(passwordEncoder.encode(details.getPassword()));
        repositoryForLogin.save(details);
        return "User registered successfully";
    }
}
