package com.fpoly.controller.admin.HoaDon.TrangThaiHoaDon;

import com.fpoly.service.TrangThaiHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DaHuyController {
    @Autowired
    TrangThaiHoaDonService trangThaiHoaDonService;

    @RequestMapping("admin/DonHang/DaHuyHang/danhSach")
    public String getHoaDonDaHuy(Model model,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "5") int size) {
        trangThaiHoaDonService.getHoaDonDaHuy(model, page, size);
        return "admin/hoadon/TrangThaiHoaDon/DaHuy";
    }

    @RequestMapping("admin/DonHang/DaHuyHang/timKiem/{duLieuTimKiem}")
    public String timKiemHoaDonDaHuy(Model model,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "5") int size,
                                     @PathVariable("duLieuTimKiem") String duLieuTimKiem) {
        trangThaiHoaDonService.timKiemHoaDonDaHuy(model, page, size, duLieuTimKiem);
        return "admin/hoadon/TrangThaiHoaDon/DaHuy";
    }

    @RequestMapping("admin/DonHang/DaHuyHang/Ngay/{duLieuTimKiem}")
    public String timKiemHoaDonTheoNgayDaHuy(Model model,
                                             @RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "5") int size,
                                             @PathVariable("duLieuTimKiem") String duLieuTimKiemString) {
        trangThaiHoaDonService.timKiemHoaDonTheoNgayDaHuy(model, page, size, duLieuTimKiemString);
        return "admin/hoadon/TrangThaiHoaDon/DaHuy";
    }
}
