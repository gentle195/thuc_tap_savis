package com.fpoly.controller.customer.HoaDon;

import com.fpoly.service.HoaDonCustomerService;
import com.fpoly.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChoXacNhanCustomerController {
    @Autowired
    HoaDonService hoaDonService;

    @Autowired
    HoaDonCustomerService hoaDonCustomerService;

    @RequestMapping("customer/DonHang/ChoXacNhan")
    public String choXacNhan(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "3") int size,
                             Model model) {
        hoaDonCustomerService.danhSachChoXacNhanCustomer(page, size, model);
        return "customer/HoaDon/DanhSach/choXacNhanCustomer";
    }
    
    @RequestMapping("customer/updateHuyDon/{id}")
    public ResponseEntity<String> updateHuyDon(@PathVariable("id") Long hoaDonId) {
        return hoaDonCustomerService.updateHuyDonHangCustomer(hoaDonId);
    }

    @RequestMapping("customer/DonHang/ChiTietHoaDon/ChoXacNhan/hoa-don-id={id}")
    public String CTChoGiaoHang(@PathVariable("id") Long id, Model model) {
        hoaDonCustomerService.chiTietChoXacNhanCustomer(id, model);
        return "customer/HoaDon/ChiTietHoaDon/CTChoXacNhanCustomer";
    }

}
