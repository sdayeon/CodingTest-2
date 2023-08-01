package com.example.codingtest2.repository;

import com.example.codingtest2.entity.MCQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MCQRepository extends JpaRepository<MCQuestion, Integer> {
}
