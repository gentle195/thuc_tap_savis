package com.fpoly.QEntityGenarate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.annotations.Generated;

import com.fpoly.entity.KieuDang;
import com.fpoly.entity.SanPham;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QKieuDang is a Querydsl query type for KieuDang
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKieuDang extends EntityPathBase<KieuDang> {

    private static final long serialVersionUID = 1748597510L;

    public static final QKieuDang kieuDang = new QKieuDang("kieuDang");

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

    public final SetPath<SanPham, QSanPham> sanPhams = this.<SanPham, QSanPham>createSet("sanPhams", SanPham.class, QSanPham.class, PathInits.DIRECT2);

    public final StringPath tenKieuDang = createString("tenKieuDang");

    public QKieuDang(String variable) {
        super(KieuDang.class, forVariable(variable));
    }

    public QKieuDang(Path<? extends KieuDang> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKieuDang(PathMetadata metadata) {
        super(KieuDang.class, metadata);
    }

}

