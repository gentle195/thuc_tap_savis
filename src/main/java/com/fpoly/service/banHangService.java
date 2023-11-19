package com.fpoly.service;

import com.fpoly.dto.search.SPAndSPCTSearchDto;
import com.fpoly.entity.HoaDon;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public interface banHangService {
    String getMauSac(Long sanPhamId);

    //Bán hàng tại quầy
    ResponseEntity<String> updateXoaSanPhamBanHangTaiQuay(Long id);

    ResponseEntity<String> updateSoLuongSanPhamBanHangTaiQuay(Long id, int quantity);

    ResponseEntity<String> huyDonBanHangTaiQuay(Long id);

    void thanhToanHoaDonBanHangTaiQuay(Long id, BigDecimal tien_giam, String khuyenMai, BigDecimal tongTienHoaDon);

    ResponseEntity<Map<String, String>> themMaGiamGiaBanHangTaiQuay(String couponCode);

    ResponseEntity<Map<String, Long>> taoHoaDonBanHangTaiQuay(HoaDon hoaDon);

    void themSanPhamVaoHoaDonBanHangTaiQuay(Long kichThuocId, Long mauSacId, Long sanPhamId, long hoaDonID, Integer soLuongSanPham);

    ResponseEntity<Map<String, String>> themKhachHangVaoHoaDonTaiQuay(Long IdHoaDon, Long IDKhachHang, String TenKhachHang, String SDTKhachHang);

    //Bán hàng online
    void saveOrderBanHangOnline(BigDecimal totalAmount,
                                BigDecimal shippingFee,
                                BigDecimal tien_giam,
                                String tenGiamGia,
                                String emailNguoiNhan,
                                String diaChiGiaoHang,
                                String nguoiNhan,
                                String sdtNguoiNhan,
                                String ghiChu,
                                Long id);


    ResponseEntity<Map<String, String>> themMaGiamGiaBanHangOnline(String couponCode);

    Long  taoHoaDonBanHangOnline(List<Long> selectedCartItemIds, RedirectAttributes redirectAttributes);

    void BanHangBanHangOnline(Long id, Model model);

    void banHangBanHangTaiQuay(Long id, SPAndSPCTSearchDto dataSearch, int pageHDCT, int sizeHDCT, Model model);

    //Mua ngay
    ResponseEntity<Long> muanNgaySanPham(Long sanPhamId, Long mauSacId, Long kichCoId, Integer soLuong);

    void saveOrderMuaNgay(BigDecimal totalAmount,
                                BigDecimal shippingFee,
                                BigDecimal tien_giam,
                                String tenGiamGia,
                                String emailNguoiNhan,
                                String diaChiGiaoHang,
                                String nguoiNhan,
                                String sdtNguoiNhan,
                                String ghiChu,
                                Long id);


}
