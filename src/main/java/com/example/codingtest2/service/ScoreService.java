package com.example.codingtest2.service;

import com.example.codingtest2.dto.UserDto;
import com.example.codingtest2.entity.*;
import com.google.gson.Gson;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.codingtest2.entity.QPQResult.pQResult;
import static com.example.codingtest2.entity.QSQResult.sQResult;
import static com.example.codingtest2.entity.QSQuestion.sQuestion;
import static com.example.codingtest2.entity.QMCQuestion.mCQuestion;
import static com.example.codingtest2.entity.QMCQResult.mCQResult;
import static com.example.codingtest2.entity.QUser.user;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ScoreService {
    private final JPAQueryFactory queryFactory;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Value("${subject_question_count}")
    int sq_count;

    public List<UserDto> findUserDtoAll(){
        List<UserDto> dtoList = new ArrayList<>();
        List<User> rawList = queryFactory
                .selectFrom(user)
                .where(user.userSubmitDt.isNotNull())
                .fetch();

        for(int i=0; i< rawList.size(); i++){
            UserDto dto = UserDto.builder()
                    .userSeq(rawList.get(i).getUserSeq())
                    .userId(rawList.get(i).getUserId())
                    .userPassword(rawList.get(i).getUserPassword())
                    .userName(rawList.get(i).getUserName())
                    .userMajor(rawList.get(i).getUserMajor())
                    .userLevel(rawList.get(i).getUserLevel())
                    .userLoginDt(rawList.get(i).getUserLoginDt().format(formatter))
                    .userSubmitDt(rawList.get(i).getUserSubmitDt().format(formatter))
                    .userTestStart(rawList.get(i).getUserTestStart().format(formatter))
                    .userTestEnd(rawList.get(i).getUserTestEnd().format(formatter))
                    .build();

            dtoList.add(dto);
        }
        return dtoList;
    }

    public Map<String, Object> getPQResult(User user) {
        List<PQResult> pqResultList = queryFactory
                .selectFrom(pQResult)
                .where(pQResult.user.eq(user))
                .fetch();

        Map<String, Object> result = new HashMap<>();
        for (PQResult r : pqResultList) {
            result.put(r.getPQuestion().getPqQuestion(), r.getPqResult());
        }

        return result;
    }

    public Map<String, Object> getSQResult(User user) {
        SQResult sqResultOne = queryFactory
                .selectFrom(sQResult)
                .where(sQResult.user.eq(user))
                .fetchOne();

        Gson gson = new Gson();
        Map<String, Object> rawResult = gson.fromJson(sqResultOne.getSqResult(), Map.class);
        Map<String, Object> result = new HashMap<>();

        for(String qSeq : rawResult.keySet()){
            result.put(getSQuestion(Integer.valueOf(qSeq)).getSqQuestion(), rawResult.get(qSeq));
        }

        return result;
    }

    private SQuestion getSQuestion(Integer sqSeq){
        return queryFactory.selectFrom(sQuestion)
                .where(sQuestion.sqSeq.eq(sqSeq))
                .fetchOne();
    }

    public Map<String, Object> getMCQResult(User user) {
        MCQResult mcqResultOne = queryFactory
                .selectFrom(mCQResult)
                .where(mCQResult.user.eq(user))
                .fetchOne();

        Gson gson = new Gson();
        Map<String, Object> rawResult = gson.fromJson(mcqResultOne.getMcqResult(), Map.class);
        Map<String, Object> result = new HashMap<>();

        for(String qSeq : rawResult.keySet()){
            result.put(getMCQuestion(Integer.valueOf(qSeq)).getMcqQuestion(), rawResult.get(qSeq));
        }

        return result;
    }
    private MCQuestion getMCQuestion(Integer mcqSeq){
        return queryFactory.selectFrom(mCQuestion)
                .where(mCQuestion.mcqSeq.eq(mcqSeq))
                .fetchOne();
    }
}
