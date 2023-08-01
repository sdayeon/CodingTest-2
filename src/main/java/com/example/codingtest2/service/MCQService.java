package com.example.codingtest2.service;

import com.example.codingtest2.entity.MCQuestion;
import com.example.codingtest2.repository.MCQRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MCQService {
    private final MCQRepository mcqRepository;

    public List<MCQuestion> findAll(){
        return mcqRepository.findAll();
    }
}
