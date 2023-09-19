package com.example.codingtest2.service;

import com.example.codingtest2.dto.UserDto;
import com.example.codingtest2.entity.User;
import com.example.codingtest2.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.codingtest2.entity.QUser.user;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final JPAQueryFactory queryFactory;
    private final UserRepository userRepository;

    public Optional<User> findBySeq(Integer userSeq) {
        return userRepository.findById(userSeq);
    }

    public User findByUserId(String userId){
        return queryFactory.selectFrom(user).where(user.userId.eq(userId)).fetchOne();
    }

    public Integer loginCheck(UserDto dto){
        LocalDateTime now = LocalDateTime.now();
        User uu = queryFactory.selectFrom(user).where(user.userId.eq(dto.getUserId())).fetchOne();

        if(uu==null) return 0;

        if("admin".equals(uu.getUserId())) return -4;

        if(!uu.getUserPassword().equals(dto.getUserPassword())) return -1;

        if(!uu.getUserTestStart().isBefore(now)) return -2;
        if(!uu.getUserTestEnd().isAfter(now)) return -2;

        if(uu.getUserSubmitDt()!=null) return -3;

        return uu.getUserSeq();
    }

    public void setLoginDt(Integer userSeq){
        queryFactory.update(user)
                .set(user.userLoginDt, LocalDateTime.now())
                .where(user.userSeq.eq(userSeq))
                .execute();
    }
}