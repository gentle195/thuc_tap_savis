package com.fpoly.service.impl;

import com.fpoly.dto.search.SPAndSPCTSearchDto;
import com.fpoly.entity.*;
import com.fpoly.repository.*;
import com.fpoly.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class banHangServiceIpml implements banHangService {
    private final banHangRepository banHangRepository;

    @Autowired
    GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    KhuyenMaiRepository khuyenMaiRepository;

    @Autowired
    GiaoDichRepository giaoDichRepository;

    @Autowired
    NguoiDungRepository nguoiDungRepository;

    @Autowired
    LichSuHoaDonRepository lichSuHoaDonRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    TrangThaiService trangThaiService;

    @Autowired
    SanPhamChiTietService sanPhamChiTietService;

    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    HoaDonService hoaDonService;

    @Autowired
    HoaDonChiTietRepository2 hoaDonChiTietRepository2;

    @Autowired
    DiaChiRepository diaChiRepository;

    @Autowired
    HinhAnhRepository hinhAnhRepository;

    @Autowired
    KhachHangRepository khachHangRepository;

    public banHangServiceIpml(banHangRepository banHangRepository) {
        this.banHangRepository = banHangRepository;
    }

    @Override
    public String getMauSac(Long sanPhamId) {
        return banHangRepository.getMauSac(sanPhamId);
    }

    @Override
    public void saveOrderBanHangOnline(BigDecimal totalAmount, BigDecimal shippingFee, BigDecimal tien_giam, String tenGiamGia, String emailNguoiNhan, String diaChiGiaoHang, String nguoiNhan, String sdtNguoiNhan, String ghiChu, Long id) {
        Optional<HoaDon> optHoaDon = hoaDonRepository.findById(id);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (optHoaDon.isPresent()) {
            Optional<KhuyenMai> optionalKhuyenMai = khuyenMaiRepository.findKhuyenMaiByTenKhuyenMai(tenGiamGia);
            if (optionalKhuyenMai.isPresent()) {
                KhuyenMai khuyenMai = optionalKhuyenMai.get();
                HoaDon hoaDon = optHoaDon.get();
                if (tenGiamGia != null) {
                    hoaDon.setKhuyenMai(khuyenMai);
                } else {
                    hoaDon.setKhuyenMai(null);
                }
            }

            //LƯU HÓA ĐƠN
            HoaDon hoaDon = optHoaDon.get();
            if (tien_giam != null) {
                hoaDon.setTien_giam(tien_giam);
            } else {
                hoaDon.setTien_giam(BigDecimal.ZERO);
            }
            TrangThai tt = new TrangThai();
            tt.setId(1L);
            hoaDon.setTrangThai(tt);
            hoaDon.setNguoiNhan(nguoiNhan);
            hoaDon.setSdtNguoiNhan(sdtNguoiNhan);
            hoaDon.setGhiChu(ghiChu);
            hoaDon.setDiaChiGiaoHang(diaChiGiaoHang);
            hoaDon.setLoaiHoaDon(0);
            hoaDon.setTongTienHoaDon(totalAmount);
            hoaDon.setTienShip(shippingFee);
            hoaDon.setEmailNguoiNhan(emailNguoiNhan);
            hoaDon.setDaXoa(false);
            hoaDonRepository.save(hoaDon);

            List<HoaDonChiTiet> hoaDonChiTiets = optHoaDon.get().getHoaDonChiTiets();

            // Lặp qua danh sách hoaDonChiTiets và xóa từng bản ghi trong bảng gio_hang_chi_tiet
            for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {
                Long sanPhamChiTietId = hoaDonChiTiet.getSanPhamChiTiet().getId();
                gioHangChiTietRepository.xoaGioHangChiTiet(sanPhamChiTietId);
            }

            //LƯU TIMELINE
            GiaoDich gd = new GiaoDich();
            gd.setHoaDon(hoaDon);
            gd.setNgayCapNhat(new Date());
            gd.setNgayTao(new Date());
            gd.setNguoiCapNhat("ABC");
            gd.setNguoiTao("ABC");
            gd.setTrangThai(tt);
            giaoDichRepository.save(gd);


            Optional<NguoiDung> OptNguoiDung = nguoiDungRepository.findByEmail2(email);
            if (OptNguoiDung.isPresent()) {
                NguoiDung nguoiDung = OptNguoiDung.get();
                gd.setNguoiDung(nguoiDung);
                giaoDichRepository.save(gd);

                //Lưu lịch sử hóa đơn
                lichSuHoaDon ls = new lichSuHoaDon();
                ls.setNguoiThaoTac(nguoiDung.getTenNguoiDung());
                ls.setHoaDon(hoaDon);
                ls.setThaoTac("Đặt hàng thanh toán khi nhận hàng");
                lichSuHoaDonRepository.save(ls);
            }

            if (email == null) {
                gd.setNguoiTao(nguoiNhan);
                giaoDichRepository.save(gd);
            }

            try {
                emailService.sendOrderConfirmationEmail(emailNguoiNhan, hoaDon);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> themMaGiamGiaBanHangOnline(String couponCode) {
        Optional<KhuyenMai> optkhuyenMai = khuyenMaiRepository.findKhuyenMaiByTenKhuyenMai(couponCode);
        if (optkhuyenMai.isPresent()) {
            KhuyenMai khuyenMai = optkhuyenMai.get();
            Integer tienGiam = khuyenMai.getPhanTramGiam();
            Integer tienGiamToiDa = khuyenMai.getGiaTriToiThieu();
            String tenGiamGia = khuyenMai.getTenKhuyenMai();
            Date ngayKetThuc = khuyenMai.getNgayKetThuc();

            Map<String, String> response = new HashMap<>();
            response.put("tenGiamGia", tenGiamGia.toString());
            response.put("tienGiamToiDa", tienGiamToiDa.toString());
            response.put("tienGiam", tienGiam.toString());
            response.put("ngayKetThuc", ngayKetThuc.toString());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public Long taoHoaDonBanHangOnline(List<Long> selectedCartItemIds, RedirectAttributes redirectAttributes) {
        List<GioHangChiTiet> selectedCartItemDetails = new ArrayList<>();

        Optional<GioHangChiTiet> optionalCartItemDetail = null;
        for (Long cartItemId : selectedCartItemIds) {
            optionalCartItemDetail = gioHangChiTietRepository.findById(cartItemId);
            if (optionalCartItemDetail.isPresent()) {
                selectedCartItemDetails.add(optionalCartItemDetail.get());
            }
        }

        HoaDon hoaDon = new HoaDon();
        Integer maxId = hoaDonService.getMaxId();
        int idMax;
        String ma;

        if (maxId != null) {
            idMax = maxId + 1;
        } else {
            idMax = 1;
        }

        DecimalFormat df = new DecimalFormat("00");
        String formattedId = df.format(idMax);
        ma = "HD" + formattedId;

        hoaDon.setMaDon(ma);
        hoaDon.setNgayTao(new Date());
        hoaDon.setNguoiTao("hduong");
        hoaDon.setKhachHang(optionalCartItemDetail.get().getGioHang().getKhachHang());
        hoaDon.setLoaiHoaDon(2);
        hoaDon.setDaXoa(false);
        hoaDonRepository.save(hoaDon);

        List<HoaDonChiTiet> hoaDonChiTietList = new ArrayList<>();

        BigDecimal tongTienDonhang = BigDecimal.ZERO;

        for (GioHangChiTiet gioHangChiTiet : selectedCartItemDetails) {
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setSanPhamChiTiet(gioHangChiTiet.getSanPhamChiTiet());
            hoaDonChiTiet.setSoLuong(gioHangChiTiet.getSoLuong());
            hoaDonChiTiet.setDonGia(gioHangChiTiet.getSanPhamChiTiet().getSanPham().getGia());
            hoaDonChiTiet.setTongTien(gioHangChiTiet.getThanhTien());
            hoaDonChiTiet.setHoaDon(hoaDon);
            hoaDonChiTietList.add(hoaDonChiTiet);
            hoaDonChiTietRepository2.save(hoaDonChiTiet);

            tongTienDonhang = tongTienDonhang.add(gioHangChiTiet.getThanhTien()); // Cộng dồn tổng tiền đơn hàng
        }
        hoaDon.setTongTienDonHang(tongTienDonhang);
        hoaDon.setHoaDonChiTiets(hoaDonChiTietList);
        hoaDonRepository.save(hoaDon);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<NguoiDung> OptNguoiDung = nguoiDungRepository.findByEmail2(email);

        if (OptNguoiDung.isPresent()) {
            NguoiDung nguoiDung = OptNguoiDung.get();
            //Lưu lịch sử hóa đơn
            lichSuHoaDon ls = new lichSuHoaDon();
            ls.setNguoiThaoTac(nguoiDung.getTenNguoiDung());
            ls.setHoaDon(hoaDon);
            ls.setThaoTac("Tạo đơn hàng");
            lichSuHoaDonRepository.save(ls);
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "Chi tiết hóa đơn đã được lưu thành công");
        return hoaDon.getId();
    }

    @Override
    public void BanHangBanHangOnline(Long id, Model model) {
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        model.addAttribute("hoaDon", hoaDon);
        KhachHang khachHang = hoaDon.getKhachHang();
        if (khachHang != null) {
            long idKhachHang = khachHang.getId();
            DiaChi diaChi = diaChiRepository.findDiaChiByKhachHang(idKhachHang);
            model.addAttribute("diaChi", diaChi);
        }

        // Giảm giá
        List<KhuyenMai> giamGia = khuyenMaiRepository.getAllKhuyenMai();
        model.addAttribute("giamGia", giamGia);

        List<HoaDonChiTiet> hoaDonChiTiet = hoaDonChiTietRepository2.findHDCT(id);
        model.addAttribute("hoaDonChiTiet", hoaDonChiTiet);

        // Lấy danh sách ảnh chính của tất cả sản phẩm và lưu vào List
        List<String> tenAnhChinhList = new ArrayList<>();
        for (HoaDonChiTiet hoadonCT : hoaDonChiTiet) {
            MauSac mauSac = hoadonCT.getSanPhamChiTiet().getMauSac();
            Long sanPhamId = hoadonCT.getSanPhamChiTiet().getSanPham().getId();

            String tenAnhChinh = hinhAnhRepository.findTenAnhChinhByMauSacIdAndSanPhamId(mauSac.getId(), sanPhamId);
            tenAnhChinhList.add(tenAnhChinh);
        }

        // Lưu danh sách tên ảnh chính vào model để sử dụng trong template
        model.addAttribute("tenAnhChinhList", tenAnhChinhList);
    }

    @Override
    public void banHangBanHangTaiQuay(Long id, SPAndSPCTSearchDto dataSearch, int pageHDCT, int sizeHDCT, Model model) {
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        model.addAttribute("hoaDon", hoaDon);

        // HÓA ĐƠN CHI TIẾT VÀ PHÂN TRANG HÓA ĐƠN CHI TIẾT
        PageRequest pageableHDCT = PageRequest.of(pageHDCT - 1, sizeHDCT);
        Page<HoaDonChiTiet> hoaDonChiTiet = hoaDonChiTietRepository2.findHDCTByHoaDonId(id, pageableHDCT);
        model.addAttribute("hoaDonChiTiet", hoaDonChiTiet.getContent());
        model.addAttribute("pageHoaDonChiTiet", hoaDonChiTiet.getTotalPages());
        model.addAttribute("pageHDCT", pageHDCT);
        model.addAttribute("sizeHDCT", sizeHDCT);

        // Lấy danh sách tên ảnh chính của tất cả sản phẩm và lưu vào List
        List<String> tenAnhChinhList = new ArrayList<>();
        for (HoaDonChiTiet hoaDonCT : hoaDonChiTiet.getContent()) {
            MauSac mauSac = hoaDonCT.getSanPhamChiTiet().getMauSac();
            Long sanPhamId = hoaDonCT.getSanPhamChiTiet().getSanPham().getId();

            String tenAnhChinh = hinhAnhRepository.findTenAnhChinhByMauSacIdAndSanPhamId(mauSac.getId(), sanPhamId);
            tenAnhChinhList.add(tenAnhChinh);
        }

        // Lưu danh sách tên ảnh chính vào model để sử dụng trong template
        model.addAttribute("tenAnhChinhList", tenAnhChinhList);
    }

    @Override
    public ResponseEntity<Long> muanNgaySanPham(Long sanPhamId, Long mauSacId, Long kichCoId, Integer soLuong) {
        HoaDon hoaDon = new HoaDon();
        Integer maxId = hoaDonService.getMaxId();
        int idMax;
        String ma;

        if (maxId != null) {
            idMax = maxId + 1;
        } else {
            idMax = 1;
        }

        DecimalFormat df = new DecimalFormat("00");
        String formattedId = df.format(idMax);
        ma = "HD" + formattedId;

        hoaDon.setMaDon(ma);
        hoaDon.setNgayTao(new Date());
        hoaDon.setNguoiTao("hduong");
        hoaDon.setLoaiHoaDon(2);
        hoaDon.setDaXoa(false);
        hoaDonRepository.save(hoaDon);

        List<HoaDonChiTiet> hoaDonChiTietList = new ArrayList<>();

        BigDecimal tongTienDonhang = BigDecimal.ZERO;

        Optional<SanPhamChiTiet> optionalSanPhamChiTiet = sanPhamChiTietService.getSanPhamChiTietByMauSacSizeSanPhamId(sanPhamId, mauSacId, kichCoId);
        if (optionalSanPhamChiTiet.isPresent()) {
            SanPhamChiTiet sanPhamChiTiet = optionalSanPhamChiTiet.get();
            BigDecimal tongTienSanPham = sanPhamChiTiet.getSanPham().getGia();
            BigDecimal soLuongDecimal = BigDecimal.valueOf(soLuong);
            tongTienDonhang = tongTienSanPham.multiply(soLuongDecimal);
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
            hoaDonChiTiet.setSoLuong(soLuong);
            hoaDonChiTiet.setDonGia(sanPhamChiTiet.getSanPham().getGia());
            hoaDonChiTiet.setTongTien(tongTienDonhang);
            hoaDonChiTiet.setHoaDon(hoaDon);
            hoaDonChiTietList.add(hoaDonChiTiet);
            hoaDonChiTietRepository2.save(hoaDonChiTiet);
        }

        hoaDon.setTongTienDonHang(tongTienDonhang);
        hoaDon.setHoaDonChiTiets(hoaDonChiTietList);
        hoaDonRepository.save(hoaDon);


        //Lưu lịch sử hóa đơn
        lichSuHoaDon ls = new lichSuHoaDon();
        ls.setHoaDon(hoaDon);
        ls.setThaoTac("Tạo đơn hàng");
        lichSuHoaDonRepository.save(ls);

        return ResponseEntity.ok().body(hoaDon.getId());
    }

    @Override
    public void saveOrderMuaNgay(BigDecimal totalAmount, BigDecimal shippingFee, BigDecimal tien_giam, String tenGiamGia, String emailNguoiNhan, String diaChiGiaoHang, String nguoiNhan, String sdtNguoiNhan, String ghiChu, Long id) {
        Optional<HoaDon> optHoaDon = hoaDonRepository.findById(id);
        if (optHoaDon.isPresent()) {
            Optional<KhuyenMai> optionalKhuyenMai = khuyenMaiRepository.findKhuyenMaiByTenKhuyenMai(tenGiamGia);
            if (optionalKhuyenMai.isPresent()) {
                KhuyenMai khuyenMai = optionalKhuyenMai.get();
                HoaDon hoaDon = optHoaDon.get();
                if (tenGiamGia != null) {
                    hoaDon.setKhuyenMai(khuyenMai);
                } else {
                    hoaDon.setKhuyenMai(null);
                }
            }

            //LƯU HÓA ĐƠN
            HoaDon hoaDon = optHoaDon.get();
            if (tien_giam != null) {
                hoaDon.setTien_giam(tien_giam);
            } else {
                hoaDon.setTien_giam(BigDecimal.ZERO);
            }
            TrangThai tt = new TrangThai();
            tt.setId(1L);
            hoaDon.setTrangThai(tt);
            hoaDon.setNguoiNhan(nguoiNhan);
            hoaDon.setSdtNguoiNhan(sdtNguoiNhan);
            hoaDon.setGhiChu(ghiChu);
            hoaDon.setDiaChiGiaoHang(diaChiGiaoHang);
            hoaDon.setLoaiHoaDon(0);
            hoaDon.setTongTienHoaDon(totalAmount);
            hoaDon.setTienShip(shippingFee);
            hoaDon.setEmailNguoiNhan(emailNguoiNhan);
            hoaDon.setDaXoa(false);
            hoaDonRepository.save(hoaDon);

            List<HoaDonChiTiet> hoaDonChiTiets = optHoaDon.get().getHoaDonChiTiets();

            // Lặp qua danh sách hoaDonChiTiets và xóa từng bản ghi trong bảng gio_hang_chi_tiet
            for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {
                Long sanPhamChiTietId = hoaDonChiTiet.getSanPhamChiTiet().getId();
                gioHangChiTietRepository.xoaGioHangChiTiet(sanPhamChiTietId);
            }

            //LƯU TIMELINE
            GiaoDich gd = new GiaoDich();
            gd.setHoaDon(hoaDon);
            gd.setNgayCapNhat(new Date());
            gd.setNguoiTao(nguoiNhan);
            gd.setTrangThai(tt);
            giaoDichRepository.save(gd);

            //Lưu lịch sử hóa đơn
            lichSuHoaDon ls = new lichSuHoaDon();
            ls.setNguoiThaoTac(nguoiNhan);
            ls.setHoaDon(hoaDon);
            ls.setThaoTac("Đặt hàng thanh toán khi nhận hàng");
            lichSuHoaDonRepository.save(ls);

            try {
                emailService.sendOrderConfirmationEmail(emailNguoiNhan, hoaDon);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ResponseEntity<String> updateXoaSanPhamBanHangTaiQuay(Long id) {
        Optional<HoaDonChiTiet> optionalHoaDon = hoaDonChiTietRepository.findById(id);
        if (optionalHoaDon.isPresent()) {
            HoaDonChiTiet hoaDonCT = optionalHoaDon.get();
            hoaDonCT.setDaXoa(true);
            hoaDonChiTietRepository.save(hoaDonCT);

            HoaDon hoaDon = hoaDonCT.getHoaDon();
            hoaDon.getHoaDonChiTiets().remove(hoaDonCT);

            BigDecimal tongTien = hoaDon.getHoaDonChiTiets().stream().filter(hdct -> !hdct.isDaXoa()) // Lọc chỉ các hóa đơn chi tiết chưa bị xóa
                    .map(HoaDonChiTiet::getTongTien).reduce(BigDecimal.ZERO, BigDecimal::add);

            hoaDon.setTongTienDonHang(tongTien);
            hoaDon.setTongTienHoaDon(tongTien);
            hoaDon.setTienShip(BigDecimal.valueOf(1));
            hoaDonRepository.save(hoaDon);

            //CẬP NHẬT SỐ LƯỢNG SẢN PHẨM CHI TIẾT
            SanPhamChiTiet sanPhamChiTiet = optionalHoaDon.get().getSanPhamChiTiet();
            Integer soLuongSPCTBanDau = optionalHoaDon.get().getSanPhamChiTiet().getSoLuong();
            Integer soLuongNhapVao = optionalHoaDon.get().getSoLuong();
            Integer soLuongcapNhat = soLuongSPCTBanDau + soLuongNhapVao;

            sanPhamChiTiet.setSoLuong(soLuongcapNhat);
            sanPhamChiTietRepository.save(sanPhamChiTiet);

            String message = "Xác nhận thành công";
            return ResponseEntity.ok(message);
        } else {
            String errorMessage = "Không tìm thấy hóa đơn";
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> updateSoLuongSanPhamBanHangTaiQuay(Long id, int quantity) {
        Optional<HoaDonChiTiet> optionalHoaDonCT = hoaDonChiTietRepository.findById(id);
        if (optionalHoaDonCT.isPresent()) {
            HoaDonChiTiet hoaDonCT = optionalHoaDonCT.get();
            SanPhamChiTiet sanPhamChiTiet = optionalHoaDonCT.get().getSanPhamChiTiet();
            int soLuongBanDau = sanPhamChiTiet.getSoLuong();

            int soLuongCapNhat = soLuongBanDau - (quantity - hoaDonCT.getSoLuong());

            sanPhamChiTiet.setSoLuong(soLuongCapNhat);
            sanPhamChiTietRepository.save(sanPhamChiTiet);

            hoaDonCT.setSoLuong(quantity);

            // Cập nhật tổng tiền của hóa đơn chi tiết
            BigDecimal donGia = hoaDonCT.getDonGia();
            BigDecimal thanhTien = donGia.multiply(BigDecimal.valueOf(quantity));
            hoaDonCT.setTongTien(thanhTien);

            hoaDonChiTietRepository.save(hoaDonCT);

            // Cập nhật tổng tiền của hóa đơn
            HoaDon hoaDon = hoaDonCT.getHoaDon();
            BigDecimal tongTienHoaDon = hoaDon.getHoaDonChiTiets().stream().filter(hdct -> !hdct.isDaXoa()).map(HoaDonChiTiet::getTongTien).reduce(BigDecimal.ZERO, BigDecimal::add);
            hoaDon.setTongTienDonHang(tongTienHoaDon);
            hoaDon.setTongTienHoaDon(tongTienHoaDon);
            hoaDon.setTienShip(BigDecimal.valueOf(1));
            hoaDonRepository.save(hoaDon);

            String message = "Lưu thành công";
            return ResponseEntity.ok(message);
        } else {
            String errorMessage = "Không tìm thấy hóa đơn chi tiết";
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> huyDonBanHangTaiQuay(Long id) {
        Optional<HoaDon> otpHoaDon = hoaDonRepository.findById(id);
        if (otpHoaDon.isPresent()) {
            TrangThai trangThai = new TrangThai();
            trangThai.setId(8l);
            HoaDon hoaDon = otpHoaDon.get();
            hoaDon.setTrangThai(trangThai);
            hoaDonRepository.save(hoaDon);

            // Cập nhật số lượng sản phẩm chi tiết và tổng tiền cho toàn bộ hóa đơn chi tiết
            List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByHoaDonIdAndDaXoa(id);
            for (HoaDonChiTiet hoaDonCT : hoaDonChiTiets) {
                hoaDonCT.setDaXoa(true);
                hoaDonChiTietRepository.save(hoaDonCT);

                SanPhamChiTiet sanPhamChiTiet = hoaDonCT.getSanPhamChiTiet();
                Integer soLuongSPCTBanDau = sanPhamChiTiet.getSoLuong();
                Integer soLuongNhapVao = hoaDonCT.getSoLuong();
                Integer soLuongcapNhat = soLuongSPCTBanDau + soLuongNhapVao;

                sanPhamChiTiet.setSoLuong(soLuongcapNhat);
                sanPhamChiTietRepository.save(sanPhamChiTiet);
            }

            BigDecimal tongTien = hoaDon.getHoaDonChiTiets().stream().filter(hdct -> !hdct.isDaXoa()) // Lọc chỉ các hóa đơn chi tiết chưa bị xóa
                    .map(HoaDonChiTiet::getTongTien).reduce(BigDecimal.ZERO, BigDecimal::add);

            hoaDon.setTongTienDonHang(tongTien);
            hoaDon.setTongTienHoaDon(tongTien);
            hoaDon.setTienShip(BigDecimal.valueOf(1));
            hoaDonRepository.save(hoaDon);

            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<NguoiDung> OptNguoiDung = nguoiDungRepository.findByEmail2(email);
            if (OptNguoiDung.isPresent()) {
                NguoiDung nguoiDung = OptNguoiDung.get();
                //Lưu lịch sử hóa đơn
                lichSuHoaDon ls = new lichSuHoaDon();
                ls.setNguoiThaoTac(nguoiDung.getTenNguoiDung());
                ls.setHoaDon(hoaDon);
                ls.setThaoTac("Đã hủy đơn" + hoaDon.getMaDon());
                lichSuHoaDonRepository.save(ls);

                //Lưu nhân viên bán hàng vào hóa đơn
                hoaDon.setNguoiDung(nguoiDung);
                hoaDonRepository.save(hoaDon);
            }

            String mss = "Hủy thành công";
            return ResponseEntity.ok(mss);
        } else {
            String erro = "Không tìm thấy hóa đơn";
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public void thanhToanHoaDonBanHangTaiQuay(Long id, BigDecimal tien_giam, String khuyenMai, BigDecimal tongTienHoaDon) {
        Optional<HoaDon> optHoaDon = hoaDonRepository.findById(id);
        if (optHoaDon.isPresent()) {
            Optional<KhuyenMai> optionalKhuyenMai = khuyenMaiRepository.findKhuyenMaiByTenKhuyenMai(khuyenMai);
            if (optionalKhuyenMai.isPresent()) {
                KhuyenMai km = optionalKhuyenMai.get();
                HoaDon hoaDon = optHoaDon.get();
                Integer tienGiamToiDa = km.getGiaTriToiThieu();
                Integer tienGiamInt = Integer.valueOf(String.valueOf(tien_giam));
                if (khuyenMai != null) {
                    hoaDon.setKhuyenMai(km);
                    if (tienGiamInt >= tienGiamToiDa) {
                        hoaDon.setTien_giam(BigDecimal.valueOf(tienGiamToiDa));
                    } else {
                        hoaDon.setTien_giam(tien_giam);
                    }
                } else {
                    hoaDon.setKhuyenMai(null);
                }
            }

            TrangThai trangThai = new TrangThai();
            trangThai.setId(7l);

            //LƯU HÓA ĐƠN
            HoaDon hoaDon = optHoaDon.get();
            hoaDon.setTrangThai(trangThai);
            hoaDon.setTongTienHoaDon(tongTienHoaDon);
            hoaDonRepository.save(hoaDon);

            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<NguoiDung> OptNguoiDung = nguoiDungRepository.findByEmail2(email);
            if (OptNguoiDung.isPresent()) {
                NguoiDung nguoiDung = OptNguoiDung.get();
                //Lưu lịch sử hóa đơn
                lichSuHoaDon ls = new lichSuHoaDon();
                ls.setNguoiThaoTac(nguoiDung.getTenNguoiDung());
                ls.setHoaDon(hoaDon);
                ls.setThaoTac("Thanh toán hóa đơn" + hoaDon.getMaDon());
                lichSuHoaDonRepository.save(ls);

                //Lưu nhân viên bán hàng vào hóa đơn
                hoaDon.setNguoiDung(nguoiDung);
                hoaDonRepository.save(hoaDon);
            }
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> themMaGiamGiaBanHangTaiQuay(String couponCode) {
        Optional<KhuyenMai> optkhuyenMai = khuyenMaiRepository.findKhuyenMaiByTenKhuyenMai(couponCode);
        if (optkhuyenMai.isPresent()) {
            KhuyenMai khuyenMai = optkhuyenMai.get();
            Integer tienGiam = khuyenMai.getPhanTramGiam();
            Integer tienGiamToiDa = khuyenMai.getGiaTriToiThieu();
            String tenGiamGia = khuyenMai.getTenKhuyenMai();

            Map<String, String> response = new HashMap<>();
            response.put("tenGiamGia", tenGiamGia.toString());
            response.put("tienGiamToiDa", tienGiamToiDa.toString());
            response.put("tienGiam", tienGiam.toString());
            return ResponseEntity.ok(response);
        }
        String erro = "Lỗi";
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Map<String, Long>> taoHoaDonBanHangTaiQuay(HoaDon hoaDon) {
        Integer maxId = hoaDonService.getMaxId();
        int id;
        String ma;

        if (maxId != null) {
            id = maxId + 1;
        } else {
            id = 1;
        }

        DecimalFormat df = new DecimalFormat("00");
        String formattedId = df.format(id);

        ma = "HD" + formattedId;

        TrangThai trangThai = trangThaiService.getTrangThaiById(6L);
        hoaDon.setMaDon(ma);
        hoaDon.setNgayTao(new Date());
        hoaDon.setNguoiTao("hduong");
        hoaDon.setLoaiHoaDon(1);
        hoaDon.setTrangThai(trangThai);
        hoaDon.setTongTienHoaDon(BigDecimal.valueOf(0));
        hoaDon.setTongTienDonHang(BigDecimal.valueOf(0));
        hoaDon.setDaXoa(false);
        hoaDon.setTien_giam(BigDecimal.ZERO);
        hoaDonRepository.save(hoaDon);

        long idHoaDon = hoaDon.getId();
        Map<String, Long> response = new HashMap<>();
        response.put("idHoaDonVuaTao", idHoaDon);

        return ResponseEntity.ok(response);
    }

    @Override
    public void themSanPhamVaoHoaDonBanHangTaiQuay(Long kichThuocId, Long mauSacId, Long sanPhamId, long hoaDonID, Integer soLuongSanPham) {
        Optional<SanPhamChiTiet> optSpct = sanPhamChiTietService.getSanPhamChiTietByMauSacSizeSanPhamId(sanPhamId, mauSacId, kichThuocId);
        if (optSpct.isPresent()) {
            Optional<HoaDon> optHD = hoaDonRepository.findById(hoaDonID);
            HoaDon hoaDon = optHD.get();
            BigDecimal giaSP = optSpct.get().getSanPham().getGia();
            BigDecimal thanhTien = giaSP.multiply(BigDecimal.valueOf(soLuongSanPham));

            Optional<HoaDonChiTiet> optHdct = hoaDonChiTietRepository.findBySanPhamChiTietAndHoaDon(optSpct.get().getId(), hoaDon.getId());

            if (optHdct.isPresent()) {
                // Nếu hóa đơn chi tiết đã tồn tại, cập nhật số lượng và tổng tiền
                HoaDonChiTiet existingHdct = optHdct.get();
                Integer soLuongHienTai = existingHdct.getSoLuong();
                BigDecimal tongTienHienTai = existingHdct.getTongTien();

                existingHdct.setSoLuong(soLuongHienTai + soLuongSanPham);
                existingHdct.setTongTien(tongTienHienTai.add(thanhTien));
                hoaDonChiTietRepository.save(existingHdct);
            } else {
                // Nếu hóa đơn chi tiết chưa tồn tại, tạo mới hóa đơn chi tiết
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setSanPhamChiTiet(optSpct.get());
                hoaDonChiTiet.setHoaDon(hoaDon);
                hoaDonChiTiet.setDonGia(giaSP);
                hoaDonChiTiet.setSoLuong(soLuongSanPham);
                hoaDonChiTiet.setTongTien(thanhTien);
                hoaDonChiTiet.setDaXoa(false);
                hoaDonChiTietRepository.save(hoaDonChiTiet);

                hoaDon.getHoaDonChiTiets().add(hoaDonChiTiet);
                hoaDonRepository.save(hoaDon);
            }

            BigDecimal tongTienDonHang = hoaDon.getHoaDonChiTiets().stream()
                    .filter(hdct -> !hdct.isDaXoa())
                    .map(HoaDonChiTiet::getTongTien)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            hoaDon.setTongTienDonHang(tongTienDonHang);
            hoaDon.setTongTienHoaDon(tongTienDonHang);
            hoaDon.setTienShip(BigDecimal.ZERO);
            hoaDonRepository.save(hoaDon);

            // Cập nhật số lượng sản phẩm chi tiết
            SanPhamChiTiet sanPhamChiTiet = optSpct.get();
            Integer soLuongSPCTBanDau = sanPhamChiTiet.getSoLuong();
            Integer soLuongcapNhat = soLuongSPCTBanDau - soLuongSanPham;

            sanPhamChiTiet.setSoLuong(soLuongcapNhat);
            sanPhamChiTietRepository.save(sanPhamChiTiet);
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> themKhachHangVaoHoaDonTaiQuay(Long IdHoaDon, Long IDKhachHang, String TenKhachHang, String SDTKhachHang) {
        Map<String, String> response = new HashMap<>();
        Optional<KhachHang> optionalKhachHang = khachHangRepository.findKhachHangBySDT(SDTKhachHang);
        if (optionalKhachHang.isPresent() && IDKhachHang == 0) {
            // Nếu tồn tại, báo lỗi và không thực hiện thêm vào hóa đơn
            response.put("error", "Số điện thoại này đã được sử dụng");
            return ResponseEntity.badRequest().body(response);
        }

        Optional<HoaDon> optionalHoaDon = hoaDonRepository.findById(IdHoaDon);

        if (optionalHoaDon.isPresent()) {
            HoaDon hoaDon = optionalHoaDon.get();

            if (IDKhachHang != 0) {
                KhachHang khachHang = khachHangRepository.findKhachHangByID(IDKhachHang);
                hoaDon.setKhachHang(khachHang);
                hoaDon.setNguoiNhan(khachHang.getHoTen());
                hoaDon.setSdtNguoiNhan(khachHang.getSoDienThoai());
                hoaDonRepository.save(hoaDon);
            } else {
                hoaDon.setKhachHang(null);
                hoaDon.setNguoiNhan(TenKhachHang);
                hoaDon.setSdtNguoiNhan(SDTKhachHang);
                hoaDonRepository.save(hoaDon);

                KhachHang kh = new KhachHang();
                kh.setHoTen(TenKhachHang);
                kh.setSoDienThoai(SDTKhachHang);
                khachHangRepository.save(kh);
            }
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok().build();
    }
}
