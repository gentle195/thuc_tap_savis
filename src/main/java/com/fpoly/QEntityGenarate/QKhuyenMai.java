package com.fpoly.QEntityGenarate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.annotations.Generated;

import com.fpoly.entity.KhuyenMai;
import com.querydsl.core.types.Path;


/**
 * QKhuyenMai is a Querydsl query type for KhuyenMai
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKhuyenMai extends EntityPathBase<KhuyenMai> {

    private static final long serialVersionUID = -1910357079L;

    public static final QKhuyenMai khuyenMai = new QKhuyenMai("khuyenMai");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> giaTriToiThieu = createNumber("giaTriToiThieu", Integer.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final DateTimePath<java.util.Date> ngayBatDau = createDateTime("ngayBatDau", java.util.Date.class);

    //inherited
    public final DateTimePath<java.util.Date> ngayCapNhat = _super.ngayCapNhat;

    public final DateTimePath<java.util.Date> ngayKetThuc = createDateTime("ngayKetThuc", java.util.Date.class);

    //inherited
    public final DateTimePath<java.util.Date> ngayTao = _super.ngayTao;

    //inherited
    public final StringPath nguoiCapNhat = _super.nguoiCapNhat;

    //inherited
    public final StringPath nguoiTao = _super.nguoiTao;

    public final NumberPath<Integer> phanTramGiam = createNumber("phanTramGiam", Integer.class);

    public final StringPath tenKhuyenMai = createString("tenKhuyenMai");

    public final BooleanPath trangThai = createBoolean("trangThai");

    public final BooleanPath xoa = createBoolean("xoa");

    public QKhuyenMai(String variable) {
        super(KhuyenMai.class, forVariable(variable));
    }

    public QKhuyenMai(Path<? extends KhuyenMai> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKhuyenMai(PathMetadata metadata) {
        super(KhuyenMai.class, metadata);
    }

}

