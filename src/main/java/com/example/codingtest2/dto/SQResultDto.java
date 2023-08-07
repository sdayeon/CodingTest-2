package com.example.codingtest2.dto;

import com.example.codingtest2.entity.User;
import lombok.Data;

@Data
public class SQResultDto {
    private Integer sqResultSeq;
    private String sqResult;
    private Integer userSeq;
}
