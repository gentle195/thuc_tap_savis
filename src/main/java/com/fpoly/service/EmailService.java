package com.fpoly.service;

import com.fpoly.entity.HoaDon;
import com.fpoly.entity.HoaDonChiTiet;
import com.fpoly.entity.MauSac;
import com.fpoly.repository.HinhAnhRepository;
import com.fpoly.repository.HoaDonChiTietRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;


@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    HoaDonChiTietRepository2 hoaDonChiTietRepository2;

    @Autowired
    HinhAnhRepository hinhAnhRepository;

    public void sendOrderConfirmationEmail(String recipientEmail,
                                           HoaDon hoaDon) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("datn.ud15@gmail.com");
        helper.setTo(recipientEmail);
        helper.setSubject("Đơn hàng của bạn đã được đặt thành công");

        long idHoaDon = hoaDon.getId();

        // Lấy danh sách ảnh chính của tất cả sản phẩm và lưu vào List
        List<HoaDonChiTiet> hoaDonChiTiet = hoaDonChiTietRepository2.findHDCT(idHoaDon);
        List<String> tenAnhChinhList = new ArrayList<>();
        for (HoaDonChiTiet hoadonCT : hoaDonChiTiet) {
            MauSac mauSac = hoadonCT.getSanPhamChiTiet().getMauSac();
            Long sanPhamId = hoadonCT.getSanPhamChiTiet().getSanPham().getId();

            String tenAnhChinh = hinhAnhRepository.findTenAnhChinhByMauSacIdAndSanPhamId(mauSac.getId(), sanPhamId);
            tenAnhChinhList.add(tenAnhChinh);
        }

        // Tạo context và thêm thông tin đơn hàng vào mẫu email
        Context context = new Context();
        context.setVariable("id", hoaDon.getId());
        context.setVariable("nguoiNhan", hoaDon.getNguoiNhan());
        context.setVariable("tongTien", hoaDon.getTongTienHoaDon());
        context.setVariable("hoaDonChiTiet", hoaDon.getHoaDonChiTiets());
        context.setVariable("hoaDon", hoaDon);
        context.setVariable("trangThai", hoaDon.getTrangThai().getName());
        context.setVariable("tenAnhChinhList", tenAnhChinhList);
        // Thêm các thông tin khác của đơn hàng vào context nếu cần

        String emailContent = templateEngine.process("admin/banHang/banHangOnline/guiMail", context);

        helper.setText(emailContent, true);

        mailSender.send(message);
    }

    public void sendMailChoGiaoHang(String recipientEmail,
                                    HoaDon hoaDon) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("datn.ud15@gmail.com");
        helper.setTo(recipientEmail);
        helper.setSubject("Đơn hàng của bạn đã được đặt thành công");

        long idHoaDon = hoaDon.getId();

        // Lấy danh sách ảnh chính của tất cả sản phẩm và lưu vào List
        List<HoaDonChiTiet> hoaDonChiTiet = hoaDonChiTietRepository2.findHDCT(idHoaDon);
        List<String> tenAnhChinhList = new ArrayList<>();
        for (HoaDonChiTiet hoadonCT : hoaDonChiTiet) {
            MauSac mauSac = hoadonCT.getSanPhamChiTiet().getMauSac();
            Long sanPhamId = hoadonCT.getSanPhamChiTiet().getSanPham().getId();

            String tenAnhChinh = hinhAnhRepository.findTenAnhChinhByMauSacIdAndSanPhamId(mauSac.getId(), sanPhamId);
            tenAnhChinhList.add(tenAnhChinh);
        }

        // Tạo context và thêm thông tin đơn hàng vào mẫu email
        Context context = new Context();
        context.setVariable("id", hoaDon.getId());
        context.setVariable("nguoiNhan", hoaDon.getNguoiNhan());
        context.setVariable("tongTien", hoaDon.getTongTienHoaDon());
        context.setVariable("hoaDonChiTiet", hoaDon.getHoaDonChiTiets());
        context.setVariable("hoaDon", hoaDon);
        context.setVariable("trangThai", hoaDon.getTrangThai().getName());
        context.setVariable("tenAnhChinhList", tenAnhChinhList);
        // Thêm các thông tin khác của đơn hàng vào context nếu cần

        String emailContent = templateEngine.process("/admin/hoadon/MailTrangThaiDon/ChoGiaoHangMail", context);

        helper.setText(emailContent, true);

        mailSender.send(message);
    }

    public void sendMailDangGiaoHang(String recipientEmail,
                                     HoaDon hoaDon) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("datn.ud15@gmail.com");
        helper.setTo(recipientEmail);
        helper.setSubject("Đơn hàng của bạn đã được đặt thành công");

        long idHoaDon = hoaDon.getId();

        // Lấy danh sách ảnh chính của tất cả sản phẩm và lưu vào List
        List<HoaDonChiTiet> hoaDonChiTiet = hoaDonChiTietRepository2.findHDCT(idHoaDon);
        List<String> tenAnhChinhList = new ArrayList<>();
        for (HoaDonChiTiet hoadonCT : hoaDonChiTiet) {
            MauSac mauSac = hoadonCT.getSanPhamChiTiet().getMauSac();
            Long sanPhamId = hoadonCT.getSanPhamChiTiet().getSanPham().getId();

            String tenAnhChinh = hinhAnhRepository.findTenAnhChinhByMauSacIdAndSanPhamId(mauSac.getId(), sanPhamId);
            tenAnhChinhList.add(tenAnhChinh);
        }

        // Tạo context và thêm thông tin đơn hàng vào mẫu email
        Context context = new Context();
        context.setVariable("id", hoaDon.getId());
        context.setVariable("nguoiNhan", hoaDon.getNguoiNhan());
        context.setVariable("tongTien", hoaDon.getTongTienHoaDon());
        context.setVariable("hoaDonChiTiet", hoaDon.getHoaDonChiTiets());
        context.setVariable("hoaDon", hoaDon);
        context.setVariable("trangThai", hoaDon.getTrangThai().getName());
        context.setVariable("tenAnhChinhList", tenAnhChinhList);
        // Thêm các thông tin khác của đơn hàng vào context nếu cần

        String emailContent = templateEngine.process("/admin/hoadon/MailTrangThaiDon/DangGiaoHangMail", context);

        helper.setText(emailContent, true);

        mailSender.send(message);
    }

    public void sendMailDaGiaoHang(String recipientEmail,
                                   HoaDon hoaDon) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("datn.ud15@gmail.com");
        helper.setTo(recipientEmail);
        helper.setSubject("Đơn hàng của bạn đã được đặt thành công");

        long idHoaDon = hoaDon.getId();

        // Lấy danh sách ảnh chính của tất cả sản phẩm và lưu vào List
        List<HoaDonChiTiet> hoaDonChiTiet = hoaDonChiTietRepository2.findHDCT(idHoaDon);
        List<String> tenAnhChinhList = new ArrayList<>();
        for (HoaDonChiTiet hoadonCT : hoaDonChiTiet) {
            MauSac mauSac = hoadonCT.getSanPhamChiTiet().getMauSac();
            Long sanPhamId = hoadonCT.getSanPhamChiTiet().getSanPham().getId();

            String tenAnhChinh = hinhAnhRepository.findTenAnhChinhByMauSacIdAndSanPhamId(mauSac.getId(), sanPhamId);
            tenAnhChinhList.add(tenAnhChinh);
        }

        // Tạo context và thêm thông tin đơn hàng vào mẫu email
        Context context = new Context();
        context.setVariable("id", hoaDon.getId());
        context.setVariable("nguoiNhan", hoaDon.getNguoiNhan());
        context.setVariable("tongTien", hoaDon.getTongTienHoaDon());
        context.setVariable("hoaDonChiTiet", hoaDon.getHoaDonChiTiets());
        context.setVariable("hoaDon", hoaDon);
        context.setVariable("trangThai", hoaDon.getTrangThai().getName());
        context.setVariable("tenAnhChinhList", tenAnhChinhList);
        // Thêm các thông tin khác của đơn hàng vào context nếu cần

        String emailContent = templateEngine.process("/admin/hoadon/MailTrangThaiDon/DaGiaoHangMail", context);

        helper.setText(emailContent, true);

        mailSender.send(message);
    }

    public void sendMailHuyDonHang(String recipientEmail,
                                   HoaDon hoaDon) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("datn.ud15@gmail.com");
        helper.setTo(recipientEmail);
        helper.setSubject("Đơn hàng của bạn đã được đặt thành công");

        long idHoaDon = hoaDon.getId();

        // Lấy danh sách ảnh chính của tất cả sản phẩm và lưu vào List
        List<HoaDonChiTiet> hoaDonChiTiet = hoaDonChiTietRepository2.findHDCT(idHoaDon);
        List<String> tenAnhChinhList = new ArrayList<>();
        for (HoaDonChiTiet hoadonCT : hoaDonChiTiet) {
            MauSac mauSac = hoadonCT.getSanPhamChiTiet().getMauSac();
            Long sanPhamId = hoadonCT.getSanPhamChiTiet().getSanPham().getId();

            String tenAnhChinh = hinhAnhRepository.findTenAnhChinhByMauSacIdAndSanPhamId(mauSac.getId(), sanPhamId);
            tenAnhChinhList.add(tenAnhChinh);
        }

        // Tạo context và thêm thông tin đơn hàng vào mẫu email
        Context context = new Context();
        context.setVariable("id", hoaDon.getId());
        context.setVariable("nguoiNhan", hoaDon.getNguoiNhan());
        context.setVariable("tongTien", hoaDon.getTongTienHoaDon());
        context.setVariable("hoaDonChiTiet", hoaDon.getHoaDonChiTiets());
        context.setVariable("hoaDon", hoaDon);
        context.setVariable("trangThai", hoaDon.getTrangThai().getName());
        context.setVariable("tenAnhChinhList", tenAnhChinhList);
        // Thêm các thông tin khác của đơn hàng vào context nếu cần

        String emailContent = templateEngine.process("/admin/hoadon/MailTrangThaiDon/DaHuyMail", context);

        helper.setText(emailContent, true);

        mailSender.send(message);
    }
}
