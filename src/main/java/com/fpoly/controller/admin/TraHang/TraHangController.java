package com.fpoly.controller.admin.TraHang;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/tra-hang")
public class TraHangController {

    @GetMapping("/list")
    public String index() {
        return "admin/trahang/list";
    }
}
