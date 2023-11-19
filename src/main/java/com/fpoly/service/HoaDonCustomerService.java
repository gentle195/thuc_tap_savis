package com.fpoly.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface HoaDonCustomerService {
    //Chờ giao hàng
    void danhSachChoGiaoHangCustomer(int page, int size, Model model);

    void chiTietChoGiaoHangCustomer(Long id, Model model);

    //Chờ xác nhận
    void danhSachChoXacNhanCustomer(int page, int size, Model model);

    void chiTietChoXacNhanCustomer(Long id, Model model);

    ResponseEntity<String> updateHuyDonHangCustomer(Long hoaDonId);

    //Đã giao
    void danhSachDaGiaoCustomer(int page, int size, Model model);

    void chiTietDaGiaoCustomer(Long id, Model model);

    //Đã hủy
    void danhSachDaHuyCustomer(int page, int size, Model model);

    void chiTietDaHuyCustomer(Long id, Model model);

    //Đang giao
    void danhSachDangGiaoCustomer(int page, int size, Model model);

    void chiTietDangGiaoCustomer(Long id, Model model);

    ResponseEntity<String> updateGiaoHangThanhCongCustomer(Long hoaDonId);

    ResponseEntity<String> updateThanhCongAllCustomer();
}
