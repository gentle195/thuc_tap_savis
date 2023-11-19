package com.fpoly.controller.customer.HoaDon;

import com.fpoly.service.HoaDonCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DangGiaoCustomerController {
    @Autowired
    HoaDonCustomerService hoaDonCustomerService;

    @RequestMapping("customer/DonHang/DangGiaoHang")
    public String DangGiaoCustomer(Model model,
                                   @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "3") int size) {
        hoaDonCustomerService.danhSachDangGiaoCustomer(page, size, model);
        return "customer/HoaDon/DanhSach/dangGiaoCustomer";
    }

    @RequestMapping("customer/DonHang/ChiTietHoaDon/DangGiaoHang/hoa-don-id={id}")
    public String CTDangGiaoCustomer(@PathVariable("id") Long id,
                                     Model model) {
        hoaDonCustomerService.chiTietDangGiaoCustomer(id, model);
        return "customer/HoaDon/ChiTietHoaDon/CTDangGiaoCustomer";
    }

    @RequestMapping("customer/updateGiaoHangThanhCong/{id}")
    public ResponseEntity<String> updateGiaoHangThanhCong(@PathVariable("id") Long hoaDonId) {
        return hoaDonCustomerService.updateGiaoHangThanhCongCustomer(hoaDonId);
    }

    @RequestMapping("customer/updateThanhCongAll")
    public ResponseEntity<String> updateThanhCongAll() {
        return hoaDonCustomerService.updateThanhCongAllCustomer();
    }
}
