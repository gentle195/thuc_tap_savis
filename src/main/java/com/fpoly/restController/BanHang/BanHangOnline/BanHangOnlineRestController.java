package com.fpoly.restController.BanHang.BanHangOnline;

import com.fpoly.service.banHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
public class BanHangOnlineRestController {
    @Autowired
    banHangService banHangService;

    @PostMapping("/save-order/{id}")
    public @ResponseBody Map<String, Object> saveOrder(@RequestParam("totalAmount") BigDecimal totalAmount,
                                                       @RequestParam("shippingFee") BigDecimal shippingFee,
                                                       @RequestParam("tien_giam") BigDecimal tien_giam,
                                                       @RequestParam String tenGiamGia,
                                                       @RequestParam("emailNguoiNhan") String emailNguoiNhan,
                                                       @RequestParam("diaChiGiaoHang") String diaChiGiaoHang,
                                                       @RequestParam("nguoiNhan") String nguoiNhan,
                                                       @RequestParam("sdtNguoiNhan") String sdtNguoiNhan,
                                                       @RequestParam("ghiChu") String ghiChu,
                                                       @PathVariable("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        banHangService.saveOrderBanHangOnline(totalAmount, shippingFee, tien_giam, tenGiamGia, emailNguoiNhan, diaChiGiaoHang, nguoiNhan, sdtNguoiNhan, ghiChu, id);

        response.put("success", true);
        return response;
    }

    @PostMapping("/banHang/themMaGiamGiaOnline")
    public ResponseEntity<Map<String, String>> themMaGiamGia(@RequestParam String couponCode) {
        return banHangService.themMaGiamGiaBanHangOnline(couponCode);
    }
}
