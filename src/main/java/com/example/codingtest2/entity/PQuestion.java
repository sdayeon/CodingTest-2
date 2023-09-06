package com.example.codingtest2.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "TBL_PQ")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pq_seq")
    private Integer pqSeq;

    @Column(name = "pq_question")
    private String pqQuestion;

    @Column(name = "pq_level")
    private String pqLevel;

    @Column(name = "pq_img")
    private String pqImg;

    @Column(name = "pq_comment1")
    private String pgComment1;

    @Column(name = "pq_comment2")
    private String pgComment2;

    @Column(name = "pq_example")
    private String pgExample;

    public PQuestion(Integer pqSeq){
        this.pqSeq = pqSeq;
    }
}