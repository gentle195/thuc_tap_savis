package com.fpoly.service.impl;

import com.fpoly.entity.GiaoDich;
import com.fpoly.entity.HoaDon;
import com.fpoly.repository.GiaoDichRepository;
import com.fpoly.repository.HoaDonRepository;
import com.fpoly.service.GiaoDichService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiaoDichServiceImpl implements GiaoDichService {
    private GiaoDichRepository giaoDichRepository;
    @Override
    public List<GiaoDich> getChiTietHoaDon(int trangThai, Long hoaDonId) {
        return giaoDichRepository.findByTrangThaiIdAndHoaDonId(trangThai, hoaDonId);
    }
}
