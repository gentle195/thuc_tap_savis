package com.fpoly.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface TrangThaiHoaDonService {
    //Chờ giao hàng
    void getHoaDonChoLayHang(Model model, int page, int size);

    void timKiemHoaDonChoGiaoHang(Model model, int page, int size, String duLieuTimKiem);

    void timKiemHoaDonTheoNgayChoGiaoHang(Model model, int page, int size, String duLieuTimKiemString);

    //Chờ xác nhận
    void getHoaDonChoXacNhan(Model model, int page, int size);

    void timKiemHoaDonChoXacNhanDonHang(Model model, int page, int size, String duLieuTimKiem);

    void timKiemHoaDonTheoNgayChoXacNhan(Model model, int page, int size, String duLieuTimKiemString);

    //Đã giao
    void getHoaDonDaGiao(Model model, int page, int size);

    void timKiemHoaDonDaGiaoHang(Model model, int page, int size, String duLieuTimKiem);

    void timKiemHoaDonTheoNgayDaGiaoHang(Model model, int page, int size, String duLieuTimKiemString);

    //Đã Hủy
    void getHoaDonDaHuy(Model model, int page, int size);

    void timKiemHoaDonDaHuy(Model model, int page, int size, String duLieuTimKiem);

    void timKiemHoaDonTheoNgayDaHuy(Model model, int page, int size, String duLieuTimKiemString);

    //Đang giao
    void getHoaDonDangGiao(Model model, int page, int size);

    void timKiemHoaDonDangGiao(Model model, int page, int size, String duLieuTimKiem);

    void timKiemHoaDonTheoNgayDangGiao(Model model, int page, int size, String duLieuTimKiemString);
}
