package com.fpoly.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gio_hang_chi_tiet")
@EqualsAndHashCode(callSuper=false)
public class GioHangChiTiet extends BaseEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "san_pham_chi_tiet_id")
    private SanPhamChiTiet sanPhamChiTiet;

    @ManyToOne
    @JoinColumn(name = "gio_hang_id")
    private GioHang gioHang;

    @Column(name = "so_luong", columnDefinition = "int not null")
    private int soLuong;

    @Column(name = "don_gia", columnDefinition = "int")
    private int donGia;

    @Column(name = "tong_tien", columnDefinition = "varchar(50) not null")
    private BigDecimal thanhTien;

    @Column(name = "trang_thai", columnDefinition = "int default(0) not null")
    private int trangThai;

    @Column(name = "da_xoa", columnDefinition = "Bit")
    private Boolean daXoa;

    @Override
    public String toString() {
        return "GioHangChiTiet{" +
                "sanPhamChiTiet=" + sanPhamChiTiet.getId() +
                ", gioHang=" + gioHang.getId() +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                ", thanhTien=" + thanhTien +
                ", trangThai=" + trangThai +
                ", daXoa=" + daXoa +
                '}';
    }
}
