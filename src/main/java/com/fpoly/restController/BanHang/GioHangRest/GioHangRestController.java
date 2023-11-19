package com.fpoly.restController.BanHang.GioHangRest;

import com.fpoly.repository.SanPhamChiTietRepository;
import com.fpoly.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class GioHangRestController {
    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    GioHangService gioHangService;

    @RequestMapping("/customer/SoLuongSanPhamChiTiet")
    public Map<String, Object> laySoLuongSanPhamChiTiet(@RequestParam("tenKichCo") String tenKichCo,
                                                        @RequestParam("mauSacId") Long mauSacId,
                                                        @RequestParam("sanPhamId") Long sanPhamId) {
        Map<String, Object> response = new HashMap<>();

        Integer soLuongSanPhamChiTiet = sanPhamChiTietRepository.laySoLuongSanPhamChiTiet(tenKichCo, mauSacId, sanPhamId);
        response.put("soLuongSanPhamChiTiet", soLuongSanPhamChiTiet);
        return response;
    }

    @PostMapping("/customer/addToCart")
    public ResponseEntity<String> addToCart(@RequestParam("sanPhamId") Long sanPhamId,
                                            @RequestParam("mauSacId") Long mauSacId,
                                            @RequestParam("kichCoId") Long kichCoId,
                                            @RequestParam("soLuong") Integer soLuong) {

        return gioHangService.addToCart(sanPhamId, mauSacId, kichCoId, soLuong);
    }

    @RequestMapping("/customer/gio-hang-chi-tiet/xoa-sach-gio-hang")
    public @ResponseBody Map<String, Object> xoaSachGioHang(@RequestParam long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            gioHangService.xoaSachGioHang(id);
            // Gửi phản hồi thành công về cho AJAX
            response.put("success", true);
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            response.put("success", false);
            response.put("error", "Giỏ hàng không tồn tại");
        }
        return response;
    }
}
