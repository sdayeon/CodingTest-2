package com.example.codingtest2.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserDto {
    private Integer userSeq;
    private String userId;
    private String userPassword;
    private String userName;
    private String userMajor;
    private String userLevel;
    private String userLoginDt;
    private String userSubmitDt;
    private String userTestStart;
    private String userTestEnd;
    private String userScoreAll;

    @Builder
    public UserDto(Integer userSeq, String userId, String userPassword, String userName, String userMajor, String userLevel, String userLoginDt, String userSubmitDt, String userTestStart, String userTestEnd, String userScoreAll) {
        this.userSeq = userSeq;
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userMajor = userMajor;
        this.userLevel = userLevel;
        this.userLoginDt = userLoginDt;
        this.userSubmitDt = userSubmitDt;
        this.userTestStart = userTestStart;
        this.userTestEnd = userTestEnd;
        this.userScoreAll = userScoreAll;
    }
}