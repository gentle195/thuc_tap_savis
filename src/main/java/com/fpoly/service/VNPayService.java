package com.fpoly.service;

import com.fpoly.controller.vnpay.Config;
import com.fpoly.entity.*;
import com.fpoly.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VNPayService {
    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    NguoiDungRepository nguoiDungRepository;

    @Autowired
    KhuyenMaiRepository khuyenMaiRepository;

    @Autowired
    GiaoDichRepository giaoDichRepository;

    @Autowired
    LichSuHoaDonRepository lichSuHoaDonRepository;

    @Autowired
    GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    EmailService emailService;

    public String createOrder(long total,
                              String orderInfor,
                              String emailNguoiNhann,
                              BigDecimal tienGiamGia,
                              String nameGiamGia,
                              String sdtNguoiNhan,
                              BigDecimal tienShipHD,
                              String hoaDonId,
                              String nguoiNhan,
                              String diaChiGiaoHang,
                              String ghiChu) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(email);
        long idNguoiDung = nguoiDung.getId();

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = Config.vnp_TmnCode;
        String orderType = "order-type";

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(total * 100));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfor);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = "vn";
        vnp_Params.put("vnp_Locale", locate);

        vnp_Params.put("vnp_ReturnUrl", Config.getUrl(emailNguoiNhann, tienGiamGia, nameGiamGia, sdtNguoiNhan, tienShipHD, hoaDonId, idNguoiDung));
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        Optional<HoaDon> optHoaDon = hoaDonRepository.findById(Long.valueOf(hoaDonId));
        if (optHoaDon.isPresent()) {
            HoaDon hoaDon = optHoaDon.get();
            hoaDon.setNguoiNhan(nguoiNhan);
            hoaDon.setDiaChiGiaoHang(diaChiGiaoHang);
            hoaDon.setGhiChu(ghiChu);
            hoaDonRepository.save(hoaDon);
        }
        return paymentUrl;
    }

    public int orderReturn(HttpServletRequest request) {
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
            String fieldName = null;
            String fieldValue = null;
            try {
                fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }

        if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
            return 1;
        } else {
            return 0;
        }

    }

    public void saveOrderReturn(HttpServletRequest request, Model model, long nguoiDungId) {
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        String emailNguoiNhan = request.getParameter("emailNguoiNhann");
        String tienGiamGiaString = request.getParameter("tienGiamGia");
        String nameGiamGia = request.getParameter("nameGiamGia");
        String sdtNguoiNhan = request.getParameter("sdtNguoiNhan");
        String tienShipHDString = request.getParameter("tienShipHD");
        String hoaDonId = request.getParameter("hoaDonId");
        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        String vnp_TransactionStatus = request.getParameter("vnp_TransactionStatus");

        BigDecimal tienGiamGia = new BigDecimal(tienGiamGiaString);
        BigDecimal tienShipHD = new BigDecimal(tienShipHDString);
        BigDecimal soTien = new BigDecimal(totalPrice);
        BigDecimal soTienHoaDon = soTien.divide(BigDecimal.valueOf(100));
        model.addAttribute("soTienHoaDon", soTienHoaDon);

        long amount = Long.parseLong(totalPrice);
        Optional<HoaDon> optHoaDon = hoaDonRepository.findById(Long.valueOf(hoaDonId));
        if (optHoaDon.isPresent()) {
            Optional<KhuyenMai> optionalKhuyenMai = khuyenMaiRepository.findKhuyenMaiByTenKhuyenMai(nameGiamGia);
            if (optionalKhuyenMai.isPresent()) {
                KhuyenMai khuyenMai = optionalKhuyenMai.get();
                HoaDon hoaDon = optHoaDon.get();
                if (nameGiamGia != null) {
                    hoaDon.setKhuyenMai(khuyenMai);
                } else {
                    hoaDon.setKhuyenMai(null);
                }
            }

            //LƯU HÓA ĐƠN
            HoaDon hoaDon = optHoaDon.get();
            if (tienGiamGia != null) {
                hoaDon.setTien_giam(tienGiamGia);
            } else {
                hoaDon.setTien_giam(BigDecimal.ZERO);
            }
            TrangThai tt = new TrangThai();
            tt.setId(1L);
            hoaDon.setTrangThai(tt);

            BigDecimal tongTienHoaDon = BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(100));
            hoaDon.setSdtNguoiNhan(sdtNguoiNhan);
            hoaDon.setLoaiHoaDon(0);
            hoaDon.setTongTienHoaDon(tongTienHoaDon);
            hoaDon.setTienShip(tienShipHD);
            hoaDon.setEmailNguoiNhan(emailNguoiNhan);
            hoaDon.setDaXoa(false);
            hoaDonRepository.save(hoaDon);

            //LƯU TIMELINE
            GiaoDich gd = new GiaoDich();
            gd.setHoaDon(hoaDon);
            gd.setNgayCapNhat(new Date());
            gd.setNgayTao(new Date());
            gd.setNguoiCapNhat("ABC");
            gd.setNguoiTao("ABC");
            gd.setTrangThai(tt);
            giaoDichRepository.save(gd);

            Optional<NguoiDung> OptNguoiDung = nguoiDungRepository.findById(nguoiDungId);
            if (OptNguoiDung.isPresent()) {
                NguoiDung nguoiDung = OptNguoiDung.get();
                gd.setNguoiDung(nguoiDung);
                giaoDichRepository.save(gd);

                //Lưu lịch sử hóa đơn
                lichSuHoaDon ls = new lichSuHoaDon();
                ls.setNguoiThaoTac(nguoiDung.getTenNguoiDung());
                ls.setHoaDon(hoaDon);
                ls.setThaoTac("Đã thanh toán qua vnPay");
                lichSuHoaDonRepository.save(ls);
            }

            List<HoaDonChiTiet> hoaDonChiTiets = optHoaDon.get().getHoaDonChiTiets();

            // Lặp qua danh sách hoaDonChiTiets và xóa từng bản ghi trong bảng gio_hang_chi_tiet
            for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {
                Long sanPhamChiTietId = hoaDonChiTiet.getSanPhamChiTiet().getId();
                gioHangChiTietRepository.xoaGioHangChiTiet(sanPhamChiTietId);
            }

            try {
                emailService.sendOrderConfirmationEmail(emailNguoiNhan, hoaDon);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

    }


    //Mua ngay
    public String createOrderMuaNgay(long total,
                                     String orderInfor,
                                     String emailNguoiNhann,
                                     BigDecimal tienGiamGia,
                                     String nameGiamGia,
                                     String sdtNguoiNhan,
                                     BigDecimal tienShipHD,
                                     String hoaDonId,
                                     String nguoiNhan,
                                     String diaChiGiaoHang,
                                     String ghiChu) {

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = Config.vnp_TmnCode;
        String orderType = "order-type";

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(total * 100));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfor);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = "vn";
        vnp_Params.put("vnp_Locale", locate);

        vnp_Params.put("vnp_ReturnUrl", Config.getUrlMuaNgay(emailNguoiNhann, tienGiamGia, nameGiamGia, sdtNguoiNhan, tienShipHD, hoaDonId));
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        Optional<HoaDon> optHoaDon = hoaDonRepository.findById(Long.valueOf(hoaDonId));
        if (optHoaDon.isPresent()) {
            HoaDon hoaDon = optHoaDon.get();
            hoaDon.setNguoiNhan(nguoiNhan);
            hoaDon.setDiaChiGiaoHang(diaChiGiaoHang);
            hoaDon.setGhiChu(ghiChu);
            hoaDonRepository.save(hoaDon);
        }
        return paymentUrl;
    }

    public void saveOrderReturnMuaNgay(HttpServletRequest request,
                                       Model model) {
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        String emailNguoiNhan = request.getParameter("emailNguoiNhann");
        String tienGiamGiaString = request.getParameter("tienGiamGia");
        String nameGiamGia = request.getParameter("nameGiamGia");
        String sdtNguoiNhan = request.getParameter("sdtNguoiNhan");
        String tienShipHDString = request.getParameter("tienShipHD");
        String hoaDonId = request.getParameter("hoaDonId");
        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        String vnp_TransactionStatus = request.getParameter("vnp_TransactionStatus");

        BigDecimal tienGiamGia = new BigDecimal(tienGiamGiaString);
        BigDecimal tienShipHD = new BigDecimal(tienShipHDString);
        BigDecimal soTien = new BigDecimal(totalPrice);
        BigDecimal soTienHoaDon = soTien.divide(BigDecimal.valueOf(100));
        model.addAttribute("soTienHoaDon", soTienHoaDon);

        long amount = Long.parseLong(totalPrice);
        Optional<HoaDon> optHoaDon = hoaDonRepository.findById(Long.valueOf(hoaDonId));
        if (optHoaDon.isPresent()) {
            Optional<KhuyenMai> optionalKhuyenMai = khuyenMaiRepository.findKhuyenMaiByTenKhuyenMai(nameGiamGia);
            if (optionalKhuyenMai.isPresent()) {
                KhuyenMai khuyenMai = optionalKhuyenMai.get();
                HoaDon hoaDon = optHoaDon.get();
                if (nameGiamGia != null) {
                    hoaDon.setKhuyenMai(khuyenMai);
                } else {
                    hoaDon.setKhuyenMai(null);
                }
            }

            //LƯU HÓA ĐƠN
            HoaDon hoaDon = optHoaDon.get();
            if (tienGiamGia != null) {
                hoaDon.setTien_giam(tienGiamGia);
            } else {
                hoaDon.setTien_giam(BigDecimal.ZERO);
            }
            TrangThai tt = new TrangThai();
            tt.setId(1L);
            hoaDon.setTrangThai(tt);

            BigDecimal tongTienHoaDon = BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(100));
            hoaDon.setSdtNguoiNhan(sdtNguoiNhan);
            hoaDon.setLoaiHoaDon(0);
            hoaDon.setTongTienHoaDon(tongTienHoaDon);
            hoaDon.setTienShip(tienShipHD);
            hoaDon.setEmailNguoiNhan(emailNguoiNhan);
            hoaDon.setDaXoa(false);
            hoaDonRepository.save(hoaDon);

            //LƯU TIMELINE
            GiaoDich gd = new GiaoDich();
            gd.setHoaDon(hoaDon);
            gd.setNgayCapNhat(new Date());
            gd.setNgayTao(new Date());
            gd.setNguoiCapNhat("ABC");
            gd.setNguoiTao("ABC");
            gd.setTrangThai(tt);
            giaoDichRepository.save(gd);

            //Lưu lịch sử hóa đơn
            lichSuHoaDon ls = new lichSuHoaDon();
            ls.setNguoiThaoTac(hoaDon.getNguoiNhan());
            ls.setHoaDon(hoaDon);
            ls.setThaoTac("Đã thanh toán qua vnPay");
            lichSuHoaDonRepository.save(ls);

            List<HoaDonChiTiet> hoaDonChiTiets = optHoaDon.get().getHoaDonChiTiets();

            // Lặp qua danh sách hoaDonChiTiets và xóa từng bản ghi trong bảng gio_hang_chi_tiet
            for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTiets) {
                Long sanPhamChiTietId = hoaDonChiTiet.getSanPhamChiTiet().getId();
                gioHangChiTietRepository.xoaGioHangChiTiet(sanPhamChiTietId);
            }

            try {
                emailService.sendOrderConfirmationEmail(emailNguoiNhan, hoaDon);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

    }
}
