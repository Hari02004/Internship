package com.student.demo;

import com.student.demo.entity.RegisterDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface RepositoryForLogin extends JpaRepository<RegisterDetails, Long>{
    Optional<RegisterDetails> findByEmail(String email);
}
