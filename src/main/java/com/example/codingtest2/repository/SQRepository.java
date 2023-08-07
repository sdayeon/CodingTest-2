package com.example.codingtest2.repository;

import com.example.codingtest2.entity.SQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SQRepository extends JpaRepository<SQuestion, Integer> {
}
