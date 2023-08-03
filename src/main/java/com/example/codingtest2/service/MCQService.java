package com.example.codingtest2.service;

import com.example.codingtest2.dto.MCQResultDto;
import com.example.codingtest2.entity.MCQResult;
import com.example.codingtest2.entity.MCQuestion;
import com.example.codingtest2.entity.User;
import com.example.codingtest2.repository.MCQRepository;
import com.example.codingtest2.repository.MCQResultRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

//import static com.example.codingtest2.entity.QUser.user;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MCQService {
    private final JPAQueryFactory queryFactory;
    private final MCQRepository mcqRepository;
    private final MCQResultRepository mcqResultRepository;
    private final UserService userService;

    public List<MCQuestion> findAll() {
        return mcqRepository.findAll();
    }

    public void insertResult(MCQResultDto dto) {
        User uu = userService.findBySeq(dto.getUserSeq()).get();
        MCQResult result = MCQResult.builder()
                .mcqResult(dto.getMcqResult())
                .user(uu)
                .build();

        mcqResultRepository.save(result);
//        queryFactory.update(user)
//                .set(user.submitDt, LocalDateTime.now())
//                .where(user.userSeq.eq(uu.getUserSeq()))
//                .execute();
    }
}