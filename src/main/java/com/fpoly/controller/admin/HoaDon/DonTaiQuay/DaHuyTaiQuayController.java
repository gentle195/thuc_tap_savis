package com.fpoly.controller.admin.HoaDon.DonTaiQuay;

import com.fpoly.service.DonHangTaiQuayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DaHuyTaiQuayController {
    @Autowired
    DonHangTaiQuayService donHangTaiQuayService;

    @RequestMapping("admin/DaHuyTaiQuay/danhSach")
    public String daHuyTaiQuay(Model model,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "5") int size) {
        donHangTaiQuayService.daHuyTaiQuay(model, page, size);
        return "admin/hoadon/DonTaiQuay/daHuyTaiQuay";
    }

    @RequestMapping("admin/DaHuyTaiQuay/TimKiem/{duLieuTimKiem}")
    public String timKiemHoaDOnTaiQuayDaHuy(Model model,
                                            @RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "5") int size,
                                            @PathVariable("duLieuTimKiem") String duLieuTimKiem) {
        donHangTaiQuayService.timKiemHoaDOnTaiQuayDaHuy(model, page, size, duLieuTimKiem);
        return "admin/hoadon/DonTaiQuay/daHuyTaiQuay";
    }

    @RequestMapping("admin/DaHuyTaiQuay/Ngay/{duLieuTimKiem}")
    public String timKiemTheoNgayHoaDontaiQuayDaHuy(Model model,
                                                    @RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "5") int size,
                                                    @PathVariable("duLieuTimKiem") String duLieuTimKiemString) {
        donHangTaiQuayService.timKiemTheoNgayHoaDontaiQuayDaHuy(model, page, size, duLieuTimKiemString);
        return "admin/hoadon/DonTaiQuay/daHuyTaiQuay";
    }
}
