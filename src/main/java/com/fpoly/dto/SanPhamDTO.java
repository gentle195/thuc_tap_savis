package com.fpoly.dto;

import com.fpoly.entity.HinhAnh;
import com.fpoly.entity.SanPhamChiTiet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamDTO {
    private Long id;

    private Long kieuDangId;

    private Long chatLieuId;

    private Long loaiHangId;

    private Long phongCachId;

    private String tenSanPham;

    private BigDecimal giaBan;

    private BigDecimal gia;

    private String moTa;

    private Boolean daXoa;

    private String tenChatLieu;

    private String TenKieuDang;

    private String tenPhongCach;

    private String tenLoaiSanPham;

    private List<HinhAnh> hinhAnhs;

    private String tenHinhAnh;
    
    private List<SanPhamChiTiet> sanPhamChiTiets;
}
