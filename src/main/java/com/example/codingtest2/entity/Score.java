package com.example.codingtest2.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "TBL_SCORE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_seq")
    private Integer scoreSeq;

    @OneToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @Column(name = "score_pq")
    private Integer scorePq;

    @Column(name = "score_sq")
    private Integer scoreSq;

    @Column(name = "score_mcq")
    private Integer scoreMcq;

    @Column(name = "score_all")
    private Integer scoreAll;
}
