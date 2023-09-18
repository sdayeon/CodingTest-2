package com.example.codingtest2.service;

import com.example.codingtest2.entity.User;
import com.example.codingtest2.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.example.codingtest2.entity.QUser.user;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ScoreService {
    private final JPAQueryFactory queryFactory;
    private final UserRepository userRepository;

    public List<User> findUserAll(){
        return queryFactory.selectFrom(user).where(user.userSubmitDt.isNotNull()).fetch();
    }
}
