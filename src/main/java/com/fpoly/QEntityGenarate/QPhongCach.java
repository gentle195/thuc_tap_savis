package com.fpoly.QEntityGenarate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.annotations.Generated;

import com.fpoly.entity.PhongCach;
import com.fpoly.entity.SanPham;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPhongCach is a Querydsl query type for PhongCach
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPhongCach extends EntityPathBase<PhongCach> {

    private static final long serialVersionUID = 592882353L;

    public static final QPhongCach phongCach = new QPhongCach("phongCach");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final BooleanPath daXoa = createBoolean("daXoa");

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.util.Date> ngayCapNhat = _super.ngayCapNhat;

    //inherited
    public final DateTimePath<java.util.Date> ngayTao = _super.ngayTao;

    //inherited
    public final StringPath nguoiCapNhat = _super.nguoiCapNhat;

    //inherited
    public final StringPath nguoiTao = _super.nguoiTao;

    public final ListPath<SanPham, QSanPham> sanPhams = this.<SanPham, QSanPham>createList("sanPhams", SanPham.class, QSanPham.class, PathInits.DIRECT2);

    public final StringPath tenPhongCach = createString("tenPhongCach");

    public QPhongCach(String variable) {
        super(PhongCach.class, forVariable(variable));
    }

    public QPhongCach(Path<? extends PhongCach> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPhongCach(PathMetadata metadata) {
        super(PhongCach.class, metadata);
    }

}

