package com.example.codingtest2.repository;

import com.example.codingtest2.entity.PQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PQRepository extends JpaRepository<PQuestion, Integer> {
}
