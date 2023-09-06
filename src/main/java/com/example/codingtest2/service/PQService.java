package com.example.codingtest2.service;

import com.example.codingtest2.dto.PQResultDto;
import com.example.codingtest2.entity.PQResult;
import com.example.codingtest2.entity.PQuestion;
import com.example.codingtest2.entity.User;
import com.example.codingtest2.repository.PQResultRepository;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.codingtest2.entity.QPQuestion.pQuestion;
import static com.example.codingtest2.entity.QPQResult.pQResult;
import static com.example.codingtest2.entity.QUser.user;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PQService {
    private final JPAQueryFactory queryFactory;
    private final PQResultRepository pqResultRepository;
    private final UserService userService;

    public List<PQuestion> findByLevel(String level){
        return queryFactory.selectFrom(pQuestion)
                .where(pQuestion.pqLevel.eq(level))
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(2)
                .fetch();
    }

    public void saveResult(PQResultDto dto) {
        User uu = userService.findBySeq(dto.getUserSeq()).get();
        int resultSeq = findPQResult(uu, dto.getPqSeq());

        if(resultSeq == 0){
            pqResultRepository.save(
                    PQResult.builder()
                            .pQuestion(new PQuestion(dto.getPqSeq()))
                            .pqResult(dto.getPqResult())
                            .user(uu)
                            .build());
        } else {
            queryFactory.update(pQResult)
                    .set(pQResult.pqResult, dto.getPqResult())
                    .where(user.userSeq.eq(uu.getUserSeq())
                            , pQResult.pQuestion.pqSeq.eq(dto.getPqSeq()))
                    .execute();
        }
    }

    private int findPQResult(User uu, int pqSeq){
        List<PQResult> result = queryFactory
                .selectFrom(pQResult)
                .where(pQResult.user.eq(uu), pQResult.pQuestion.pqSeq.eq(pqSeq))
                .fetch();

        if(result.isEmpty()){
            log.info("INSERT PQ RESULT");
            return 0;
        } else {
            log.info("UPDATE PQ RESULT : {}", pqSeq);
            return pqSeq;
        }
    }
}
