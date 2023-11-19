package com.fpoly.QEntityGenarate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.annotations.Generated;

import com.fpoly.entity.HinhAnh;
import com.fpoly.entity.SanPham;
import com.fpoly.entity.SanPhamChiTiet;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSanPham is a Querydsl query type for SanPham
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSanPham extends EntityPathBase<SanPham> {

    private static final long serialVersionUID = -546909310L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSanPham sanPham = new QSanPham("sanPham");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QChatLieu chatLieu;

    public final BooleanPath daXoa = createBoolean("daXoa");

    public final NumberPath<java.math.BigDecimal> gia = createNumber("gia", java.math.BigDecimal.class);

    public final ListPath<HinhAnh, QHinhAnh> hinhAnhs = this.<HinhAnh, QHinhAnh>createList("hinhAnhs", HinhAnh.class, QHinhAnh.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final QKieuDang kieuDang;

    public final QLoaiSanPham loaiSanPham;

    public final StringPath moTa = createString("moTa");

    //inherited
    public final DateTimePath<java.util.Date> ngayCapNhat = _super.ngayCapNhat;

    //inherited
    public final DateTimePath<java.util.Date> ngayTao = _super.ngayTao;

    //inherited
    public final StringPath nguoiCapNhat = _super.nguoiCapNhat;

    //inherited
    public final StringPath nguoiTao = _super.nguoiTao;

    public final QPhongCach phongCach;

    public final ListPath<SanPhamChiTiet, QSanPhamChiTiet> sanPhamChiTiets = this.<SanPhamChiTiet, QSanPhamChiTiet>createList("sanPhamChiTiets", SanPhamChiTiet.class, QSanPhamChiTiet.class, PathInits.DIRECT2);

    public final StringPath tenSanPham = createString("tenSanPham");

    public QSanPham(String variable) {
        this(SanPham.class, forVariable(variable), INITS);
    }

    public QSanPham(Path<? extends SanPham> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSanPham(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSanPham(PathMetadata metadata, PathInits inits) {
        this(SanPham.class, metadata, inits);
    }

    public QSanPham(Class<? extends SanPham> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatLieu = inits.isInitialized("chatLieu") ? new QChatLieu(forProperty("chatLieu")) : null;
        this.kieuDang = inits.isInitialized("kieuDang") ? new QKieuDang(forProperty("kieuDang")) : null;
        this.loaiSanPham = inits.isInitialized("loaiSanPham") ? new QLoaiSanPham(forProperty("loaiSanPham")) : null;
        this.phongCach = inits.isInitialized("phongCach") ? new QPhongCach(forProperty("phongCach")) : null;
    }

}

