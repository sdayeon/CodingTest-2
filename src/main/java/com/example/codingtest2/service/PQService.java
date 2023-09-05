package com.example.codingtest2.service;

import com.example.codingtest2.dto.PQResultDto;
import com.example.codingtest2.entity.PQuestion;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.codingtest2.entity.QPQuestion.pQuestion;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PQService {
    private final JPAQueryFactory queryFactory;

    public List<PQuestion> findByLevel(String level){
        return queryFactory.selectFrom(pQuestion)
                .where(pQuestion.pqLevel.eq(level))
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(2)
                .fetch();
    }

    public void savePQResult(PQResultDto dto) {

    }
}
