package com.fpoly.controller.vnpay.VnpayMuaNgay;

import com.fpoly.service.VNPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Controller
public class VnpayMuaNgayController {
    @Autowired
    VNPayService vnPayService;

    @PostMapping("MuaNgay/payment/create")
    public String createPayment(@RequestParam("orderCode") String orderCode,
                                @RequestParam("amount") long amount,
                                @RequestParam("diaChiGiaoHang") String diaChiGiaoHang,
                                @RequestParam("nguoiNhan") String nguoiNhan,
                                @RequestParam("emailNguoiNhann") String emailNguoiNhan,
                                @RequestParam("tienGiamGia") BigDecimal tienGiamGia,
                                @RequestParam("nameGiamGia") String nameGiamGia,
                                @RequestParam("sdtNguoiNhan") String sdtNguoiNhan,
                                @RequestParam("ghiChu") String ghiChu,
                                @RequestParam("tienShipHD") BigDecimal tienShipHD) {

        String vnpayUrl = vnPayService.createOrderMuaNgay(amount, orderCode, emailNguoiNhan, tienGiamGia, nameGiamGia, sdtNguoiNhan, tienShipHD, orderCode, nguoiNhan, diaChiGiaoHang, ghiChu);
        return "redirect:" + vnpayUrl;
    }

    @RequestMapping("MuaNgay/payment/return")
    public String handleReturn(Model model, HttpServletRequest request) {
        int paymentStatus = vnPayService.orderReturn(request);

        if (paymentStatus == 1) {
            vnPayService.saveOrderReturnMuaNgay(request, model);
            return "vnp/MuaNgay/SuccessMuaNgay";
        } else {
            return "vnp/MuaNgay/errorMuaNgay";
        }
    }
}
