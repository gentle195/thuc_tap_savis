package com.fpoly.service;

import java.math.BigDecimal;

import com.fpoly.dto.GioHangDTO;
import org.springframework.http.ResponseEntity;

public interface GioHangService {

	GioHangDTO findByKhachHangId(Long i);

	void capNhatTongTien(Long i);

	int tinhTienGioHangTheoMaGioHangChiTiet(long[] idGioHangChiTiet);

	ResponseEntity<String> addToCart(Long sanPhamId, Long mauSacId, Long kichCoId, Integer soLuong);

	void xoaSachGioHang(long id);
}
