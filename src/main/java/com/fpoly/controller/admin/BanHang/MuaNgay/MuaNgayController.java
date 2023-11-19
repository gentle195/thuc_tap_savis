package com.fpoly.controller.admin.BanHang.MuaNgay;

import com.fpoly.service.banHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MuaNgayController {
    @Autowired
    banHangService banHangService;

    @RequestMapping("MuaNgaySanPham/checkout/{id}")
    public String banHangBanHangOnline(@PathVariable("id") Long id, Model model) {
        banHangService.BanHangBanHangOnline(id, model);
        return "customer/MuaNgay/MuaNgayCheckOut";
    }
}
