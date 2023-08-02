package com.example.codingtest2.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1251489005L;

    public static final QUser user = new QUser("user");

    public final DateTimePath<java.time.LocalDateTime> loginDt = createDateTime("loginDt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> submitDt = createDateTime("submitDt", java.time.LocalDateTime.class);

    public final StringPath userId = createString("userId");

    public final StringPath userPassword = createString("userPassword");

    public final NumberPath<Integer> userSeq = createNumber("userSeq", Integer.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

