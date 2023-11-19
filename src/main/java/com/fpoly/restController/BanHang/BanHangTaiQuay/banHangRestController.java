package com.fpoly.restController.BanHang.BanHangTaiQuay;

import com.fpoly.entity.*;
import com.fpoly.repository.*;
import com.fpoly.service.banHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
public class banHangRestController {
    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    banHangService banHangService;

    @Autowired
    HoaDonRepoditory2 hoaDonRepoditory2;

    @RequestMapping("/banHang/laySoLuongSanPhamChiTiet")
    public Map<String, Object> laySoLuongSanPhamChiTiet(@RequestParam("tenKichCo") String tenKichCo, @RequestParam("mauSacId") Long mauSacId, @RequestParam("sanPhamId") Long sanPhamId) {
        Map<String, Object> response = new HashMap<>();

        Integer soLuongSanPhamChiTiet = sanPhamChiTietRepository.laySoLuongSanPhamChiTiet(tenKichCo, mauSacId, sanPhamId);
        response.put("soLuongSanPhamChiTiet", soLuongSanPhamChiTiet);
        return response;
    }

    //XÓA SẢN PHẨM TRONG ĐƠN HÀNG
    @RequestMapping("/update-XoaSP/{id}")
    public ResponseEntity<String> updateXoaSP(@PathVariable("id") Long id) {
        return banHangService.updateXoaSanPhamBanHangTaiQuay(id);
    }

    //UPDATE SỐ LƯỢNG CỦA HÓA ĐƠN CHI TIẾT
    @PostMapping("/update-SoLuong/{id}")
    public ResponseEntity<String> updateSoLuong(@PathVariable("id") Long id, @RequestParam("quantity") int quantity) {
        return banHangService.updateSoLuongSanPhamBanHangTaiQuay(id, quantity);
    }

    //HỦY ĐƠN HÀNG VÀ CẬP NHẬT LẠI SỐ LƯỢNG SẢN PHẨM
    @RequestMapping("/HuyDon/{id}")
    public ResponseEntity<String> huyDon(@PathVariable("id") Long id) {
        return banHangService.huyDonBanHangTaiQuay(id);
    }

    @PostMapping("/banHangtaiQuay/thanhToan/{id}")
    public @ResponseBody Map<String, Object> thanhToan(@PathVariable("id") Long id,
                                                       @RequestParam("tien_giam") BigDecimal tien_giam,
                                                       @RequestParam String khuyenMai,
                                                       @RequestParam("tongTienHoaDon") BigDecimal tongTienHoaDon) {
        Map<String, Object> response = new HashMap<>();
        banHangService.thanhToanHoaDonBanHangTaiQuay(id, tien_giam, khuyenMai, tongTienHoaDon);
        response.put("success", true);
        return response;
    }

    @RequestMapping("/banHang/themMaGiamGia-taiQuay")
    public ResponseEntity<Map<String, String>> apDungMaGiamGia(@RequestParam String couponCode) {
        return banHangService.themMaGiamGiaBanHangTaiQuay(couponCode);
    }

    //Kiểm tra số lượng hóa đơn chờ hiện có
    @RequestMapping("/KiemTraSoLuongHoaDonChoHienCo")
    public ResponseEntity<Boolean> checkHoaDonCho() {
        boolean vuotQuaNguong = hoaDonRepoditory2.soLuongHoaDonCho() >= 8;
        return ResponseEntity.ok(vuotQuaNguong);
    }

    //Tạo hóa đơn
    @PostMapping("/TaoHoaDon")
    public ResponseEntity<Map<String, Long>> taoHoaDon(HoaDon hoaDon) {
        return banHangService.taoHoaDonBanHangTaiQuay(hoaDon);
    }

    @PostMapping("/banHang/themSanPhamVaoHoaDon")
    public @ResponseBody Map<String, Object> ThemSanPhamVaoHoaDon(@RequestParam("kichThuocId") Long kichThuocId,
                                                                  @RequestParam("mauSacId") Long mauSacId,
                                                                  @RequestParam("sanPhamId") Long sanPhamId,
                                                                  @RequestParam("hoaDonId") long hoaDonID,
                                                                  @RequestParam("soLuongSanPham") Integer soLuongSanPham) {
        Map<String, Object> response = new HashMap<>();

        banHangService.themSanPhamVaoHoaDonBanHangTaiQuay(kichThuocId, mauSacId, sanPhamId, hoaDonID, soLuongSanPham);
        response.put("success", true);
        return response;
    }

    @PostMapping("/ThemThongTinKhachHang")
    public ResponseEntity<Map<String, String>> themThongTinKhachHang(@RequestParam Long IdHoaDon,
                                                                     @RequestParam Long IDKhachHang,
                                                                     @RequestParam String TenKhachHang,
                                                                     @RequestParam String SDTKhachHang) {

        return banHangService.themKhachHangVaoHoaDonTaiQuay(IdHoaDon, IDKhachHang, TenKhachHang, SDTKhachHang);
    }
}
