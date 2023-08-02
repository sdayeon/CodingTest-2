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

    public Integer loginCheck(UserDto dto){
        User uu = queryFactory.selectFrom(user).where(user.userId.eq(dto.getUserId())).fetchOne();
        if(uu==null) return 0;

        if(uu.getUserPassword().equals(dto.getUserPassword()))
            return uu.getUserSeq();

        return 0;
    }

    public void loginTimeCheck(Integer userSeq){
        queryFactory.update(user)
                .set(user.loginDt, LocalDateTime.now())
                .where(user.userSeq.eq(userSeq))
                .execute();
    }
}
