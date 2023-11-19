package com.fpoly.controller.admin.HoaDon.TrangThaiHoaDon;

import com.fpoly.service.TrangThaiHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChoGiaoHangController {
    @Autowired
    TrangThaiHoaDonService trangThaiHoaDonService;

    @RequestMapping("admin/DonHang/ChoGiaoHang/danhSach")
    public String getHoaDonChoLayHang(Model model,
                                      @RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "5") int size) {

        trangThaiHoaDonService.getHoaDonChoLayHang(model, page, size);
        return "admin/hoadon/TrangThaiHoaDon/ChoGiaoHang";
    }

    @RequestMapping("admin/DonHang/ChoGiaoHang/timKiem/{duLieuTimKiem}")
    public String timKiemHoaDonChoGiaoHang(Model model,
                                           @RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "5") int size,
                                           @PathVariable("duLieuTimKiem") String duLieuTimKiem) {
        trangThaiHoaDonService.timKiemHoaDonChoGiaoHang(model, page, size, duLieuTimKiem);
        return "admin/hoadon/TrangThaiHoaDon/ChoGiaoHang";
    }

    @RequestMapping("admin/DonHang/ChoGiaoHang/Ngay/{duLieuTimKiem}")
    public String timKiemHoaDonTheoNgayChoGiaoHang(Model model,
                                                   @RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "5") int size,
                                                   @PathVariable("duLieuTimKiem") String duLieuTimKiemString) {
        trangThaiHoaDonService.timKiemHoaDonTheoNgayChoGiaoHang(model, page, size, duLieuTimKiemString);
        return "admin/hoadon/TrangThaiHoaDon/ChoGiaoHang";
    }
}
