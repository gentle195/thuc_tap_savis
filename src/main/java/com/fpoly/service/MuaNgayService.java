package com.fpoly.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface MuaNgayService {
    ResponseEntity<String> MuaNgaySanPham(Long sanPhamId, Long mauSacId, Long kichCoId, Integer soLuong);
}
