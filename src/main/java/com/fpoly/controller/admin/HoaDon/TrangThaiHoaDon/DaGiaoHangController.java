package com.fpoly.controller.admin.HoaDon.TrangThaiHoaDon;

import com.fpoly.service.TrangThaiHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DaGiaoHangController {
    @Autowired
    TrangThaiHoaDonService trangThaiHoaDonService;

    @RequestMapping("admin/DonHang/DaGiaoHang/danhSach")
    public String getHoaDonDaGiao(Model model,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "5") int size) {
        trangThaiHoaDonService.getHoaDonDaGiao(model, page, size);
        return "admin/hoadon/TrangThaiHoaDon/DaGiao";
    }

    @RequestMapping("admin/DonHang/DaGiaoHang/timKiem/{duLieuTimKiem}")
    public String timKiemHoaDonDaGiaoHang(Model model,
                                          @RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "5") int size,
                                          @PathVariable("duLieuTimKiem") String duLieuTimKiem) {
        trangThaiHoaDonService.timKiemHoaDonDaGiaoHang(model, page, size, duLieuTimKiem);
        return "admin/hoadon/TrangThaiHoaDon/DaGiao";
    }

    @RequestMapping("admin/DonHang/DaGiaoHang/Ngay/{duLieuTimKiem}")
    public String timKiemHoaDonTheoNgayDaGiaoHang(Model model,
                                                  @RequestParam(defaultValue = "1") int page,
                                                  @RequestParam(defaultValue = "5") int size,
                                                  @PathVariable("duLieuTimKiem") String duLieuTimKiemString) {
        trangThaiHoaDonService.timKiemHoaDonTheoNgayDaGiaoHang(model, page, size, duLieuTimKiemString);
        return "admin/hoadon/TrangThaiHoaDon/DaGiao";
    }
}
