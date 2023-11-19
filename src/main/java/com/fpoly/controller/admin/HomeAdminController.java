package com.fpoly.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeAdminController {

    @RequestMapping("admin/home")
    public String homeAdmin() {
        return "admin/home/home";
    }
}
