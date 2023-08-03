package com.example.codingtest2.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "TBL_USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_seq")
    private Integer userSeq;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_login_dt")
    private LocalDateTime userLoginDt;

    @Column(name = "user_submit_dt")
    private LocalDateTime userSubmitDt;

    @Column(name = "user_test_start")
    private LocalDateTime userTestStart;

    @Column(name = "user_test_end")
    private LocalDateTime userTestEnd;
}