package com.example.codingtest2.service;

import com.example.codingtest2.dto.MCQResultDto;
import com.example.codingtest2.entity.MCQResult;
import com.example.codingtest2.entity.MCQuestion;
import com.example.codingtest2.entity.User;
import com.example.codingtest2.repository.MCQRepository;
import com.example.codingtest2.repository.MCQResultRepository;
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
    private final MCQResultRepository mcqResultRepository;
    private final UserService userService;

    public List<MCQuestion> findAll() {
        return mcqRepository.findAll();
    }

    public void insertResult(MCQResultDto dto) {
        User user = userService.findBySeq(dto.getUserSeq()).get();
        MCQResult result = MCQResult.builder()
                .mcqResult(dto.getMcqResult())
                .user(user)
                .build();

        mcqResultRepository.save(result);
    }
}