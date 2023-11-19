package com.fpoly.restController.HoaDon.Admin;

import com.fpoly.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DangGiaoHangAdminRestController {
    @Autowired
    HoaDonService hoaDonService;

    //UPDATE TRẠNG THÁI CỦA ĐƠN HÀNG LÀ ĐÃ GIAO HÀNG THÀNH CÔNG(ĐÃ GIAO HÀNG)
    @RequestMapping("/updateGiaoHangThanhCong/{id}")
    public ResponseEntity<String> updateGiaoHangThanhCong(@PathVariable("id") Long hoaDonId) {
        return hoaDonService.updateGiaoHangThanhCongDangGiaoHang(hoaDonId);
    }

    @RequestMapping("/updateThanhCongAll")
    public ResponseEntity<String> updateThanhCongAll() {
        return hoaDonService.updateThanhCongAllDangGiaoHang();
    }
}
