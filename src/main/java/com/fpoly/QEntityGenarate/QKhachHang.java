package com.fpoly.QEntityGenarate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.annotations.Generated;

import com.fpoly.entity.DiaChi;
import com.fpoly.entity.KhachHang;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QKhachHang is a Querydsl query type for KhachHang
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKhachHang extends EntityPathBase<KhachHang> {

    private static final long serialVersionUID = 1186222585L;

    public static final QKhachHang khachHang = new QKhachHang("khachHang");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath email = createString("email");

    public final StringPath hoTen = createString("hoTen");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final ListPath<DiaChi, QDiaChi> listDiaChi = this.<DiaChi, QDiaChi>createList("listDiaChi", DiaChi.class, QDiaChi.class, PathInits.DIRECT2);

    public final StringPath matKhau = createString("matKhau");

    //inherited
    public final DateTimePath<java.util.Date> ngayCapNhat = _super.ngayCapNhat;

    //inherited
    public final DateTimePath<java.util.Date> ngayTao = _super.ngayTao;

    //inherited
    public final StringPath nguoiCapNhat = _super.nguoiCapNhat;

    //inherited
    public final StringPath nguoiTao = _super.nguoiTao;

    public final StringPath soDienThoai = createString("soDienThoai");

    public final NumberPath<Integer> soLanMua = createNumber("soLanMua", Integer.class);

    public final NumberPath<Integer> trangThai = createNumber("trangThai", Integer.class);

    public QKhachHang(String variable) {
        super(KhachHang.class, forVariable(variable));
    }

    public QKhachHang(Path<? extends KhachHang> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKhachHang(PathMetadata metadata) {
        super(KhachHang.class, metadata);
    }

}

