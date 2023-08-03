package com.example.codingtest2.service;

import com.example.codingtest2.dto.MCQResultDto;
import com.example.codingtest2.entity.MCQuestion;
import com.example.codingtest2.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MCQResultService {
    private final MCQService mcqService;

    public Integer setResultScore(MCQResultDto dto, User uu){
        String level = uu.getUserLevel();
        List<MCQuestion> qList = mcqService.findByLevel(level);
        List<String> result = new ArrayList<>();
        int finalScore = 0;

        for(MCQuestion mcq : qList){
            String rString = "\""+mcq.getMcqSeq()+"\":\""+mcq.getMcqAnswer()+"\"";
            result.add(rString);
        }

        for(String score : result) {
            if(dto.getMcqResult().contains(score)){
                finalScore++;
            }
        }

        return finalScore;
    }
}
