package com.example.codingtest2.dto;

import lombok.Data;

@Data
public class MCQResultDto {
    private Integer mcqResultSeq;
    private String mcqResult;
    private Integer userSeq;
    private Integer mcqResultScore;
}
