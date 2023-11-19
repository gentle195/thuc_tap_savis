package com.fpoly.service;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public interface InHoaDonService {
    //Bán hàng
    ResponseEntity<byte[]> generatePdf(Long hoaDonId);

    //Đơn tại quầy
    ResponseEntity<byte[]> generatePdfDonTaiQuay(Long hoaDonId);

    //Đơn đặt hàng
    ResponseEntity<byte[]> generatePdfDonDatHang(Long hoaDonId);
}
