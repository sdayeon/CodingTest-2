package com.example.codingtest2.repository;

import com.example.codingtest2.entity.SQResult;
import com.example.codingtest2.entity.SQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SQResultRepository extends JpaRepository<SQResult, Integer> {
}
