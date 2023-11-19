package com.fpoly.QEntityGenarate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.annotations.Generated;

import com.fpoly.entity.LoaiSanPham;
import com.fpoly.entity.SanPham;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLoaiSanPham is a Querydsl query type for LoaiSanPham
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLoaiSanPham extends EntityPathBase<LoaiSanPham> {

    private static final long serialVersionUID = -242910953L;

    public static final QLoaiSanPham loaiSanPham = new QLoaiSanPham("loaiSanPham");

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

    public final StringPath tenLoaiSanPham = createString("tenLoaiSanPham");

    public QLoaiSanPham(String variable) {
        super(LoaiSanPham.class, forVariable(variable));
    }

    public QLoaiSanPham(Path<? extends LoaiSanPham> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLoaiSanPham(PathMetadata metadata) {
        super(LoaiSanPham.class, metadata);
    }

}

