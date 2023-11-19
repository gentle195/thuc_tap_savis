package com.fpoly.service;

import com.fpoly.dto.search.SPAndSPCTSearchDto;
import com.fpoly.entity.HoaDon;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
public interface HoaDonService {
    List<HoaDon> getAll();

    Integer getMaxId();

    //Chỉnh sửa hóa đơn
    void capNhatTrangThaiHuyDon(String maDonHang);

    ResponseEntity<String> xoaHoaDonCTChinhSuaHoaDon(Long hoaDonCTId);

    ResponseEntity<String> chinhSuaSoLuongSanPhamChinhSuaHoaDon(Long id, int quantity);

    void ThemSanPhamVaoHoaDonChoXacNhanChinhSuaHoaDon(Long kichThuocId, Long mauSacId, Long sanPhamId, long hoaDonID, Integer soLuongSanPham);

    void XoaHoaDonCXNChinhSuaHoaDon(Long hoaDonID);

    void ChinhSuaHoaDonView(Long hoaDonId, SPAndSPCTSearchDto dataSearch, Optional<Integer> page, Optional<Integer> size, Model model, Optional<String> messageSuccess, Optional<String> messageDanger);

    //Chờ giao hàng
    ResponseEntity<String> updateGiaoHangChoGiaoHang(Long hoaDonId);

    ResponseEntity<String> updateGiaoHangAllChoGiaoHang();

    //Chờ xác nhận
    ResponseEntity<String> updateXacNhanChoXacNhan(Long hoaDonId);

    ResponseEntity<String> updateHuyDonChoXacNhan(Long hoaDonId);

    ResponseEntity<String> updateXacNhanAllChoXacNhan();

    ResponseEntity<String> updateHuyAllChoXacNhan();

    //Đang giao hàng
    ResponseEntity<String> updateGiaoHangThanhCongDangGiaoHang(Long hoaDonId);

    ResponseEntity<String> updateThanhCongAllDangGiaoHang();


}
