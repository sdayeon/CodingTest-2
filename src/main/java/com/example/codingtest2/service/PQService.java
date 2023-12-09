package com.example.codingtest2.service;

import com.example.codingtest2.dto.PQDto;
import com.example.codingtest2.dto.PQResultDto;
import com.example.codingtest2.entity.PQResult;
import com.example.codingtest2.entity.PQuestion;
import com.example.codingtest2.entity.User;
import com.example.codingtest2.repository.PQResultRepository;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.example.codingtest2.entity.QPQResult.pQResult;
import static com.example.codingtest2.entity.QPQuestion.pQuestion;
import static com.example.codingtest2.entity.QUser.user;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PQService {
    private final JPAQueryFactory queryFactory;
    private final PQResultRepository pqResultRepository;

    public List<PQDto> findByLevel(User uu) {

        // 프로그래밍 문제
        // 레벨1-4문항, 레벨2-4문항, 레벨3-8문항

        int count = 4;
        if("3".equals(uu.getUserLevel())) count = 8;

        List<PQResult> result = queryFactory.selectFrom(pQResult).where(pQResult.user.eq(uu)).fetch();
        List<PQDto> resultDTO = new ArrayList<>();

        //임시 저장 내역이 없는 경우
        if (result.isEmpty()) {
            List<PQuestion> question = queryFactory.selectFrom(pQuestion)
                    .where(pQuestion.pqLevel.eq(uu.getUserLevel()))
                    .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                    .limit(count)
                    .fetch();

            for (PQuestion p : question) {
                pqResultRepository.save(
                        PQResult.builder()
                                .pQuestion(p)
                                .pqResult("")
                                .user(uu)
                                .build());

                resultDTO.add(PQDto.toDTO(p, ""));
            }
            return resultDTO;
        }

        //임시 저장 내역이 있는 경우
        for (PQResult r : result) {
            PQuestion question = queryFactory.selectFrom(pQuestion)
                    .where(pQuestion.pqSeq.eq(r.getPQuestion().getPqSeq()))
                    .fetchOne();

            resultDTO.add(PQDto.toDTO(question, r.getPqResult()));
        }
        return resultDTO;
    }

    public void saveResult(PQResultDto dto, User uu) {
        //개별 임시저장
        queryFactory.update(pQResult)
                .set(pQResult.pqResult, dto.getPqResult())
                .where(user.userSeq.eq(uu.getUserSeq())
                        , pQResult.pQuestion.pqSeq.eq(dto.getPqSeq()))
                .execute();
    }
}