package com.fpoly.controller.admin.BanHang.BanHangOnline;

import com.fpoly.service.banHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class banHangOnlineController {
    @Autowired
    banHangService banHangService;

    @PostMapping("/customer/gio-hang-chi-tiet/tao-hoa-don")
    public String taoHoaDon(@RequestBody List<Long> selectedCartItemIds, RedirectAttributes redirectAttributes) {
        long hoaDonID = banHangService.taoHoaDonBanHangOnline(selectedCartItemIds, redirectAttributes);
        return "redirect:/customer/checkout/" + hoaDonID;
    }

    @RequestMapping("customer/checkout/{id}")
    public String banHangBanHangOnline(@PathVariable("id") Long id, Model model) {
        banHangService.BanHangBanHangOnline(id, model);
        return "admin/banHang/banHangOnline/DatHang";
    }
}
