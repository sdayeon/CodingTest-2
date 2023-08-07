package com.example.codingtest2.service;

import com.example.codingtest2.dto.MCQResultDto;
import com.example.codingtest2.entity.MCQResult;
import com.example.codingtest2.entity.MCQuestion;
import com.example.codingtest2.entity.User;
import com.example.codingtest2.repository.MCQRepository;
import com.example.codingtest2.repository.MCQResultRepository;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.codingtest2.entity.QUser.user;
import static com.example.codingtest2.entity.QMCQuestion.mCQuestion;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MCQService {
    private final JPAQueryFactory queryFactory;
    private final MCQRepository mcqRepository;
    private final MCQResultRepository mcqResultRepository;
    private final UserService userService;

    public List<MCQuestion> findByLevel(String level){
        return queryFactory.selectFrom(mCQuestion)
                .where(mCQuestion.mcqLevel.eq(level))
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(3)
                .fetch();
    }

    public void insertResult(MCQResultDto dto) {
        User uu = userService.findBySeq(dto.getUserSeq()).get();
        MCQResult result = MCQResult.builder()
                .mcqResult(dto.getMcqResult())
                .user(uu)
                .mcqResultScore(dto.getMcqResultScore())
                .build();

        mcqResultRepository.save(result);
        queryFactory.update(user)
                .set(user.userSubmitDt, LocalDateTime.now())
                .where(user.userSeq.eq(uu.getUserSeq()))
                .execute();
    }
}