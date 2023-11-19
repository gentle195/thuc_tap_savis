package com.fpoly.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface ChiTietHoaDonService {
    void choXacNhan(Long id, Model model);

    void choGiaoHang(Long id, Model model);

    void dangGiaoHang(Long id, Model model);

    void daGiaoHang(Long id, Model model);

    void daHuy(Long id, Model model);

}
