package com.example.pielearning.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pielearning.entite.Test;
public interface TestRepository extends JpaRepository<Test, Long> {
}


