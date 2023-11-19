package com.fpoly.controller.admin.HoaDon.ChiTietHoaDon;

import com.fpoly.service.ChiTietHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChiTietHoaDonController {
    @Autowired
    ChiTietHoaDonService chiTietHoaDonService;

    //CHỜ XÁC NHẬN
    @RequestMapping("ChiTietHoaDon/ChoXacNhan/hoa-don-id={id}")
    public String ChoXacNhan(@PathVariable("id") Long id, Model model) {
        chiTietHoaDonService.choXacNhan(id, model);
        return "admin/hoadon/ChiTiethoaDon/CTChoXacNhan";
    }

    //CHỜ GIAO HÀNG
    @RequestMapping("ChiTietHoaDon/ChoGiaoHang/hoa-don-id={id}")
    public String ChoGiaoHang(@PathVariable("id") Long id, Model model) {
        chiTietHoaDonService.choGiaoHang(id, model);
        return "admin/hoadon/ChiTiethoaDon/CTChoGiaohang";
    }

    //ĐANG GIAO HÀNG
    @RequestMapping("ChiTietHoaDon/DangGiaoHang/hoa-don-id={id}")
    public String DangGiaoHang(@PathVariable("id") Long id, Model model) {
        chiTietHoaDonService.dangGiaoHang(id, model);
        return "admin/hoadon/ChiTiethoaDon/CTDangGiaoHang";
    }

    //ĐÃ GIAO HÀNG
    @RequestMapping("ChiTietHoaDon/DaGiaoHang/hoa-don-id={id}")
    public String DaGiaoHang(@PathVariable("id") Long id, Model model) {
        chiTietHoaDonService.daGiaoHang(id, model);
        return "admin/hoadon/ChiTiethoaDon/CTDaGiao";
    }

    //ĐÃ HỦY
    @RequestMapping("ChiTietHoaDon/DaHuy/hoa-don-id={id}")
    public String DaHuy(@PathVariable("id") Long id, Model model) {
        chiTietHoaDonService.daHuy(id, model);
        return "admin/hoadon/ChiTiethoaDon/CTDaHuy";
    }
}
