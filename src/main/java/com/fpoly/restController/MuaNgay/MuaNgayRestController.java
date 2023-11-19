package com.fpoly.restController.MuaNgay;

import com.fpoly.service.banHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MuaNgayRestController {
    @Autowired
    banHangService banHangService;

    @PostMapping("/MuaNgay/checkout")
    public ResponseEntity<Long> MuaNgaySanPham(@RequestParam("sanPhamId") Long sanPhamId,
                                               @RequestParam("mauSacId") Long mauSacId,
                                               @RequestParam("kichCoId") Long kichCoId,
                                               @RequestParam("soLuong") Integer soLuong) {
        return banHangService.muanNgaySanPham(sanPhamId, mauSacId, kichCoId, soLuong);
    }

    @PostMapping("MuaNgay/save-order/{id}")
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
        banHangService.saveOrderMuaNgay(totalAmount, shippingFee, tien_giam, tenGiamGia, emailNguoiNhan, diaChiGiaoHang, nguoiNhan, sdtNguoiNhan, ghiChu, id);

        response.put("success", true);
        return response;
    }
}
