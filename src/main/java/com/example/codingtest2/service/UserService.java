package com.example.codingtest2.service;

import com.example.codingtest2.entity.User;
import com.example.codingtest2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findBySeq(Integer userSeq) {
        return userRepository.findById(userSeq);
    }
}
