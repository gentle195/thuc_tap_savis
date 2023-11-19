package com.fpoly.QEntityGenarate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.annotations.Generated;

import com.fpoly.entity.TrangThai;
import com.querydsl.core.types.Path;


/**
 * QTrangThai is a Querydsl query type for TrangThai
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTrangThai extends EntityPathBase<TrangThai> {

    private static final long serialVersionUID = -1633370570L;

    public static final QTrangThai trangThai = new QTrangThai("trangThai");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QTrangThai(String variable) {
        super(TrangThai.class, forVariable(variable));
    }

    public QTrangThai(Path<? extends TrangThai> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTrangThai(PathMetadata metadata) {
        super(TrangThai.class, metadata);
    }

}

