package com.fpoly.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hoa_don_chi_tiet")
public class HoaDonChiTiet extends BaseEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "hoa_don_id")
    private HoaDon hoaDon;

    @ManyToOne
    @JoinColumn(name = "san_pham_chi_tiet_id")
    private SanPhamChiTiet sanPhamChiTiet;

    @Column(name = "don_gia", columnDefinition = "int not null")
    private BigDecimal donGia;

    @Column(name = "so_luong", columnDefinition = "int not null")
    private int soLuong;

    @Column(name = "tong_tien", columnDefinition = "int not null")
    private BigDecimal tongTien;

    @Column(name = "da_xoa", columnDefinition = "BIT")
    private boolean daXoa;

    @Override
    public String toString() {
        return "HoaDonChiTiet{" +
                "hoaDon=" + hoaDon.getId() +
                ", sanPhamChiTiet=" + sanPhamChiTiet.getId() +
                ", donGia=" + donGia +
                ", soLuong=" + soLuong +
                ", tongTien=" + tongTien +
                ", daXoa='" + daXoa + '\'' +
                '}';
    }
}
