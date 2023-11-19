package com.fpoly.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface NhanVienService {
    Map<String, Object> themMoiNhanVien(String email, String diaChi, String soDienThoai, String ho, String ten, String anhNhanVien, long ChucVu, Map<String, Object> response);

    Map<String, Object> ChinhSuaNhanVien(Long idNhanVien, String email, String diaChi, String soDienThoai, String hoTen, String anhNhanVien, long ChucVu, Map<String, Object> response);

    ResponseEntity<String> XoaNhanVien(Long id);
}
