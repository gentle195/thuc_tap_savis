package com.fpoly.controller.admin.HoaDon.DonTaiQuay;

import com.fpoly.service.DonHangTaiQuayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DaThanhToanController {
    @Autowired
    DonHangTaiQuayService donHangTaiQuayService;

    @RequestMapping("admin/DaThanhToan/danhSach")
    public String DanhSachDaThanhToan(Model model,
                                      @RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "5") int size) {
        donHangTaiQuayService.DanhSachDaThanhToan(model, page, size);
        return "admin/hoadon/DonTaiQuay/daThanhToan";
    }

    @RequestMapping("admin/DaThanhToan/timKiem/{duLieuTimKiem}")
    public String timKiemHoaDonDaThanhToan(Model model,
                                           @RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "5") int size,
                                           @PathVariable("duLieuTimKiem") String duLieuTimKiem) {
        donHangTaiQuayService.timKiemHoaDonDaThanhToan(model, page, size, duLieuTimKiem);
        return "admin/hoadon/DonTaiQuay/daThanhToan";
    }

    @RequestMapping("admin/DaThanhToan/Ngay/{duLieuTimKiem}")
    public String timKiemHoaDonDaThanhToanTheoNgayTao(Model model,
                                                      @RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "5") int size,
                                                      @PathVariable("duLieuTimKiem") String duLieuTimKiemString) {
        donHangTaiQuayService.timKiemHoaDonDaThanhToanTheoNgayTao(model, page, size, duLieuTimKiemString);
        return "admin/hoadon/DonTaiQuay/daThanhToan";
    }

}
