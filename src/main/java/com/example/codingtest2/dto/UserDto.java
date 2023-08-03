package com.example.codingtest2.dto;

import lombok.Data;

@Data
public class UserDto {
    private Integer userSeq;
    private String userId;
    private String userPassword;
    private String userLoginDt;
    private String userSubmitDt;
    private String userTestStart;
    private String userTestEnd;
    private String userLevel;
}