package com.example.codingtest2.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPQuestion is a Querydsl query type for PQuestion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPQuestion extends EntityPathBase<PQuestion> {

    private static final long serialVersionUID = 1918258324L;

    public static final QPQuestion pQuestion = new QPQuestion("pQuestion");

    public final StringPath pgComment1 = createString("pgComment1");

    public final StringPath pgComment2 = createString("pgComment2");

    public final StringPath pgExample = createString("pgExample");

    public final StringPath pqImg = createString("pqImg");

    public final StringPath pqLevel = createString("pqLevel");

    public final StringPath pqQuestion = createString("pqQuestion");

    public final NumberPath<Integer> pqSeq = createNumber("pqSeq", Integer.class);

    public QPQuestion(String variable) {
        super(PQuestion.class, forVariable(variable));
    }

    public QPQuestion(Path<? extends PQuestion> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPQuestion(PathMetadata metadata) {
        super(PQuestion.class, metadata);
    }

}

