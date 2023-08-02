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

    @Column(name = "user_loginDt")
    private LocalDateTime loginDt;

    @Column(name = "user_submitDt")
    private LocalDateTime submitDt;
}