package com.fpoly.service;

import com.fpoly.entity.GiaoDich;
import com.fpoly.entity.HoaDon;
import com.fpoly.repository.GiaoDichRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface GiaoDichService {
    List<GiaoDich> getChiTietHoaDon(int trangThai, Long hoaDonId);
}
