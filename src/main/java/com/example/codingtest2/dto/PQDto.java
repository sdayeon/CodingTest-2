package com.example.codingtest2.dto;

import com.example.codingtest2.entity.PQuestion;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PQDto {
    private Integer pqSeq;
    private String pqQuestion;
    private String pqLevel;
    private String pqImg;
    private String pqComment1;
    private String pqComment2;
    private String pqExample;
    private String savedResult;

    public static PQDto toDTO(PQuestion e, String result) {
        return PQDto.builder()
                .pqSeq(e.getPqSeq())
                .pqQuestion(e.getPqQuestion())
                .pqLevel(e.getPqLevel())
                .pqImg(e.getPqImg())
                .pqComment1(e.getPgComment1())
                .pqComment2(e.getPgComment2())
                .pqExample(e.getPgExample())
                .savedResult(result)
                .build();
    }
}
