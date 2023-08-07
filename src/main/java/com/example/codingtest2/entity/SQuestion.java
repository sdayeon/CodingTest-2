package com.example.codingtest2.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "TBL_SQ")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SQuestion {

    @Id
    @GeneratedValue
    @Column(name = "sq_seq")
    private Integer sqSeq;

    @Column(name = "sq_question")
    private String sqQuestion;

    @Column(name = "sq_answer")
    private String sqAnswer;

    @Column(name = "sq_level")
    private String sqLevel;
}