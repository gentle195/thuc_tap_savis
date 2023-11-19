package com.fpoly.QEntityGenarate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.annotations.Generated;

import com.fpoly.entity.GioHangChiTiet;
import com.fpoly.entity.HoaDonChiTiet;
import com.fpoly.entity.SanPhamChiTiet;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSanPhamChiTiet is a Querydsl query type for SanPhamChiTiet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSanPhamChiTiet extends EntityPathBase<SanPhamChiTiet> {

    private static final long serialVersionUID = 165118662L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSanPhamChiTiet sanPhamChiTiet = new QSanPhamChiTiet("sanPhamChiTiet");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final BooleanPath coHienThi = createBoolean("coHienThi");

    public final BooleanPath daXoa = createBoolean("daXoa");

    public final ListPath<GioHangChiTiet, QGioHangChiTiet> gioHangChiTiet = this.<GioHangChiTiet, QGioHangChiTiet>createList("gioHangChiTiet", GioHangChiTiet.class, QGioHangChiTiet.class, PathInits.DIRECT2);

    public final ListPath<HoaDonChiTiet, QHoaDonChiTiet> hoaDonChiTiets = this.<HoaDonChiTiet, QHoaDonChiTiet>createList("hoaDonChiTiets", HoaDonChiTiet.class, QHoaDonChiTiet.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final QKichCo kichCo;

    public final StringPath maSanPhamChiTiet = createString("maSanPhamChiTiet");

    public final QMauSac mauSac;

    //inherited
    public final DateTimePath<java.util.Date> ngayCapNhat = _super.ngayCapNhat;

    //inherited
    public final DateTimePath<java.util.Date> ngayTao = _super.ngayTao;

    //inherited
    public final StringPath nguoiCapNhat = _super.nguoiCapNhat;

    //inherited
    public final StringPath nguoiTao = _super.nguoiTao;

    public final QSanPham sanPham;

    public final NumberPath<Integer> soLuong = createNumber("soLuong", Integer.class);

    public QSanPhamChiTiet(String variable) {
        this(SanPhamChiTiet.class, forVariable(variable), INITS);
    }

    public QSanPhamChiTiet(Path<? extends SanPhamChiTiet> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSanPhamChiTiet(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSanPhamChiTiet(PathMetadata metadata, PathInits inits) {
        this(SanPhamChiTiet.class, metadata, inits);
    }

    public QSanPhamChiTiet(Class<? extends SanPhamChiTiet> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.kichCo = inits.isInitialized("kichCo") ? new QKichCo(forProperty("kichCo")) : null;
        this.mauSac = inits.isInitialized("mauSac") ? new QMauSac(forProperty("mauSac")) : null;
        this.sanPham = inits.isInitialized("sanPham") ? new QSanPham(forProperty("sanPham"), inits.get("sanPham")) : null;
    }

}

