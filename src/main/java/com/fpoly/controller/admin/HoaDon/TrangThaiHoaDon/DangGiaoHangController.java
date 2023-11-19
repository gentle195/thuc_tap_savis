package com.fpoly.controller.admin.HoaDon.TrangThaiHoaDon;

import com.fpoly.service.TrangThaiHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DangGiaoHangController {
    @Autowired
    TrangThaiHoaDonService trangThaiHoaDonService;

    @RequestMapping("admin/DonHang/DangGiaoHang/danhSach")
    public String getHoaDonDangGiao(Model model,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "5") int size) {
        trangThaiHoaDonService.getHoaDonDangGiao(model, page, size);
        return "admin/hoadon/TrangThaiHoaDon/DangGiao";
    }

    @RequestMapping("admin/DonHang/DangGiaoHang/timKiem/{duLieuTimKiem}")
    public String timKiemHoaDonDangGiao(Model model,
                                        @RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "5") int size,
                                        @PathVariable("duLieuTimKiem") String duLieuTimKiem) {
        trangThaiHoaDonService.timKiemHoaDonDangGiao(model, page, size, duLieuTimKiem);
        return "admin/hoadon/TrangThaiHoaDon/DangGiao";
    }

    @RequestMapping("admin/DonHang/DangGiaoHang/Ngay/{duLieuTimKiem}")
    public String timKiemHoaDonTheoNgayDangGiao(Model model,
                                                @RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "5") int size,
                                                @PathVariable("duLieuTimKiem") String duLieuTimKiemString) {
        trangThaiHoaDonService.timKiemHoaDonTheoNgayDangGiao(model, page, size, duLieuTimKiemString);
        return "admin/hoadon/TrangThaiHoaDon/DangGiao";
    }
}
