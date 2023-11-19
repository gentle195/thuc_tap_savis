package com.fpoly.QEntityGenarate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.annotations.Generated;

import com.fpoly.entity.KichCo;
import com.fpoly.entity.SanPhamChiTiet;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QKichCo is a Querydsl query type for KichCo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKichCo extends EntityPathBase<KichCo> {

    private static final long serialVersionUID = -2040708367L;

    public static final QKichCo kichCo = new QKichCo("kichCo");

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

    public final ListPath<SanPhamChiTiet, QSanPhamChiTiet> sanPhamChiTiets = this.<SanPhamChiTiet, QSanPhamChiTiet>createList("sanPhamChiTiets", SanPhamChiTiet.class, QSanPhamChiTiet.class, PathInits.DIRECT2);

    public final StringPath tenKichCo = createString("tenKichCo");

    public QKichCo(String variable) {
        super(KichCo.class, forVariable(variable));
    }

    public QKichCo(Path<? extends KichCo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKichCo(PathMetadata metadata) {
        super(KichCo.class, metadata);
    }

}

