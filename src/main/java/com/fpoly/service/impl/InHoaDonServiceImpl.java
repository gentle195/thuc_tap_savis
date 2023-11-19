package com.fpoly.service.impl;

import com.fpoly.entity.HoaDon;
import com.fpoly.entity.HoaDonChiTiet;
import com.fpoly.entity.KhuyenMai;
import com.fpoly.repository.HoaDonRepository;
import com.fpoly.service.InHoaDonService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Service
public class InHoaDonServiceImpl implements InHoaDonService {
    @Autowired
    HoaDonRepository hoaDonRepository;


    @Override
    public ResponseEntity<byte[]> generatePdf(Long hoaDonId) {
        Optional<HoaDon> optHoaDon = hoaDonRepository.findById(hoaDonId);
        if (optHoaDon.isPresent()) {
            HoaDon hoaDon = optHoaDon.get();
            // Tạo nội dung HTML cho hóa đơn (thay đổi cho phù hợp với mẫu HTML của bạn)
            StringBuilder htmlContentBuilder = new StringBuilder();
            htmlContentBuilder.append("<html><head>");
            htmlContentBuilder.append("<meta charset=\"UTF-8\">");
            htmlContentBuilder.append("<title>Hóa đơn</title>");
            htmlContentBuilder.append("<style>");
            htmlContentBuilder.append("body {\n" +
                    "    font-family: Arial, sans-serif;\n" +
                    "    line-height: 1.6;\n" +
                    "    background-color: #f9f9f9;\n" +
                    "    padding: 20px;\n" +
                    "}\n" +
                    "\n" +
                    "h1 {\n" +
                    "    color: #338dbc;" +
                    "    text-align: center;\n" +
                    "    font-size: 24px;\n" +
                    "    margin-bottom: 10px;\n" +
                    "}\n" +
                    "\n" +
                    "p {\n" +
                    "    margin-bottom: 10px;\n" +
                    "    color: #333;" +
                    "}\n" +
                    "\n" +
                    "h3 {\n" +
                    "    margin-bottom: 10px;\n" +
                    "    color: #333;\n" +
                    "    text-align: center;" +
                    "}\n" +
                    "\n" +
                    "table {\n" +
                    "    width: 100%;\n" +
                    "    border-collapse: collapse;\n" +
                    "    margin-top: 20px;\n" +
                    "    margin-bottom: 30px;\n" +
                    "}\n" +
                    "\n" +
                    "th, td {\n" +
                    "    padding: 12px 15px;\n" +
                    "    border-bottom: 1px solid #ddd;\n" +
                    "}\n" +
                    "\n" +
                    "th {\n" +
                    "    background-color: #f2f2f2;\n" +
                    "}\n" +
                    "\n" +
                    "tr:hover {\n" +
                    "    background-color: #f5f5f5;\n" +
                    "}\n" +
                    "\n" +
                    "h1.order-details-title {\n" +
                    "    margin-top: 40px;\n" +
                    "}\n" +
                    "\n" +
                    "p.footer-text {\n" +
                    "    margin-top: 30px;\n" +
                    "    text-align: center;\n" +
                    "    color: #888;\n" +
                    "}\n" +
                    "\n" +
                    ".container {\n" +
                    "    max-width: 600px;\n" +
                    "    margin: 0 auto;\n" +
                    "}\n" +
                    "\n" +
                    ".header {\n" +
                    "    text-align: center;\n" +
                    "    margin-bottom: 30px;\n" +
                    "}\n" +
                    "\n" +
                    ".footer {\n" +
                    "    text-align: center;\n" +
                    "    margin-top: 50px;\n" +
                    "    padding-top: 20px;\n" +
                    "    border-top: 1px solid #ddd;\n" +
                    "    color: #888;\n" +
                    "}\n" +
                    "\n" +
                    ".logo {\n" +
                    "    width: 100px;\n" +
                    "    height: auto;\n" +
                    "}\n" +
                    "\n" +
                    ".product-table {\n" +
                    "    border: 1px solid #ddd;\n" +
                    "}\n" +
                    "\n" +
                    ".product-table th, .product-table td {\n" +
                    "    text-align: left;\n" +
                    "}\n" +
                    "\n" +
                    ".total-amount {\n" +
                    "    font-weight: bold;\n" +
                    "}\n" +
                    "\n" +
                    "/* Add more styles as needed */\n");
            htmlContentBuilder.append("</style>");
            htmlContentBuilder.append("<body>");

            //Các nội dung của html
            htmlContentBuilder.append("<h1>").append("LEOPARD STUDIO").append("</h1>");

            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            // Thêm thông tin đơn hàng
            Date ngayTao = hoaDon.getNgayTao();
            String formattedTienGiam;
            KhuyenMai km = hoaDon.getKhuyenMai();

            // Kiểm tra nếu khuyến mãi là null hoặc không có id
            if (km == null || km.getId() == null) {
                formattedTienGiam = "0 VNĐ";
            } else {
                BigDecimal tienGiamToiDa = BigDecimal.valueOf(km.getGiaTriToiThieu());

                BigDecimal tienGiamHoaDon = hoaDon.getTien_giam();

                if (tienGiamHoaDon == null) {
                    formattedTienGiam = "0 VNĐ";
                } else if (tienGiamHoaDon.compareTo(tienGiamToiDa) >= 0) {
                    formattedTienGiam = numberFormat.format(tienGiamHoaDon);
                } else {
                    formattedTienGiam = tienGiamHoaDon + "%";
                }
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedNgayTao = dateFormat.format(ngayTao);

            htmlContentBuilder.append("<h3>").append("Thông tin đơn hàng").append("</h1>");
            htmlContentBuilder.append("<p>Mã đơn hàng: ").append(hoaDon.getMaDon()).append("</p>");
            htmlContentBuilder.append("<p>Ngày mua: ").append(formattedNgayTao).append("</p>");
            htmlContentBuilder.append("<p>Khách hàng: ").append(hoaDon.getNguoiNhan()).append("</p>");
            htmlContentBuilder.append("<p>Số điện thoại khách hàng: ").append(hoaDon.getSdtNguoiNhan()).append("</p>");
            htmlContentBuilder.append("<p>Trạng thái đơn: Đã thanh toán</p>");
            htmlContentBuilder.append("<p>Nhân viên bán hàng: ").append(hoaDon.getNguoiDung().getTenNguoiDung()).append("</p>");


            String formattedTongTienDonHang = numberFormat.format(hoaDon.getTongTienDonHang());
            String formattedTongTienHoaDon = numberFormat.format(hoaDon.getTongTienHoaDon());
            // Thêm chi tiết đơn hàng
            htmlContentBuilder.append("<h3>").append("Chi tiết đơn hàng").append("</h1>");
            htmlContentBuilder.append("<table>");
            htmlContentBuilder.append("<tr><th>Sản phẩm</th><th>Số lượng</th><th>Thành tiền</th></tr>");
            for (HoaDonChiTiet hoaDonChiTiet : hoaDon.getHoaDonChiTiets()) {
                NumberFormat fomatTien = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                String fomatTienSanPham = fomatTien.format(hoaDonChiTiet.getTongTien());
                htmlContentBuilder.append("<tr>");
                htmlContentBuilder.append("<td>").append(hoaDonChiTiet.getSanPhamChiTiet().getSanPham().getTenSanPham())
                        .append(" (").append(hoaDonChiTiet.getSanPhamChiTiet().getKichCo().getTenKichCo())
                        .append("/").append(hoaDonChiTiet.getSanPhamChiTiet().getMauSac().getTenMauSac()).append(")")
                        .append("</td>");

                htmlContentBuilder.append("<td>").append(hoaDonChiTiet.getSoLuong()).append("</td>");
                htmlContentBuilder.append("<td>").append(fomatTienSanPham).append("</td>");
                htmlContentBuilder.append("</tr>");
            }
            htmlContentBuilder.append("</table>");

            // Thêm tổng tiền và các thông tin khác của hóa đơn nếu cần
            htmlContentBuilder.append("<p>Tổng giá trị đơn hàng: ").append(formattedTongTienDonHang).append("</p>");
            htmlContentBuilder.append("<p>Tiền giảm: ").append(formattedTienGiam).append("</p>");
            htmlContentBuilder.append("<p>Tổng tiền thanh toán: ").append(formattedTongTienHoaDon).append("</p>");

            htmlContentBuilder.append("<h3>Xin chân thành cảm ơn sự ủng hộ của bạn dành cho LEOPARD STUDIO!</h3>");
            htmlContentBuilder.append("</body></html>");

            // Gọi phương thức tạo file PDF từ nội dung HTML, sử dụng thư viện iText
            byte[] pdfBytes = createPdfFromHtml(htmlContentBuilder);

            // Lưu file PDF vào thư mục dự án
            String projectRootPath = System.getProperty("user.dir");

            // Tạo đường dẫn động đến thư mục lưu pdf
            String filePath = projectRootPath + "/hoaDonDatt/hoa_don_" + hoaDonId + ".pdf";
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                fileOutputStream.write(pdfBytes);
            } catch (IOException e) {
                e.printStackTrace();
                // Xử lý lỗi nếu cần thiết
            }

            // Thiết lập thông tin phản hồi
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "hoa_don.pdf");

            // Trả về file PDF dưới dạng byte[]
            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<byte[]> generatePdfDonTaiQuay(Long hoaDonId) {
        Optional<HoaDon> optHoaDon = hoaDonRepository.findById(hoaDonId);
        if (optHoaDon.isPresent()) {
            HoaDon hoaDon = optHoaDon.get();
            // Tạo nội dung HTML cho hóa đơn (thay đổi cho phù hợp với mẫu HTML của bạn)
            StringBuilder htmlContentBuilder = new StringBuilder();
            htmlContentBuilder.append("<html><head>");
            htmlContentBuilder.append("<meta charset=\"UTF-8\">");
            htmlContentBuilder.append("<title>Hóa đơn</title>");
            htmlContentBuilder.append("<style>");
            htmlContentBuilder.append("body {\n" +
                    "    font-family: Arial, sans-serif;\n" +
                    "    line-height: 1.6;\n" +
                    "    background-color: #f9f9f9;\n" +
                    "    padding: 20px;\n" +
                    "}\n" +
                    "\n" +
                    "h1 {\n" +
                    "    color: #338dbc;" +
                    "    text-align: center;\n" +
                    "    font-size: 24px;\n" +
                    "    margin-bottom: 10px;\n" +
                    "}\n" +
                    "\n" +
                    "p {\n" +
                    "    margin-bottom: 10px;\n" +
                    "    color: #333;" +
                    "}\n" +
                    "\n" +
                    "h3 {\n" +
                    "    margin-bottom: 10px;\n" +
                    "    color: #333;\n" +
                    "    text-align: center;" +
                    "}\n" +
                    "\n" +
                    "table {\n" +
                    "    width: 100%;\n" +
                    "    border-collapse: collapse;\n" +
                    "    margin-top: 20px;\n" +
                    "    margin-bottom: 30px;\n" +
                    "}\n" +
                    "\n" +
                    "th, td {\n" +
                    "    padding: 12px 15px;\n" +
                    "    border-bottom: 1px solid #ddd;\n" +
                    "}\n" +
                    "\n" +
                    "th {\n" +
                    "    background-color: #f2f2f2;\n" +
                    "}\n" +
                    "\n" +
                    "tr:hover {\n" +
                    "    background-color: #f5f5f5;\n" +
                    "}\n" +
                    "\n" +
                    "h1.order-details-title {\n" +
                    "    margin-top: 40px;\n" +
                    "}\n" +
                    "\n" +
                    "p.footer-text {\n" +
                    "    margin-top: 30px;\n" +
                    "    text-align: center;\n" +
                    "    color: #888;\n" +
                    "}\n" +
                    "\n" +
                    ".container {\n" +
                    "    max-width: 600px;\n" +
                    "    margin: 0 auto;\n" +
                    "}\n" +
                    "\n" +
                    ".header {\n" +
                    "    text-align: center;\n" +
                    "    margin-bottom: 30px;\n" +
                    "}\n" +
                    "\n" +
                    ".footer {\n" +
                    "    text-align: center;\n" +
                    "    margin-top: 50px;\n" +
                    "    padding-top: 20px;\n" +
                    "    border-top: 1px solid #ddd;\n" +
                    "    color: #888;\n" +
                    "}\n" +
                    "\n" +
                    ".logo {\n" +
                    "    width: 100px;\n" +
                    "    height: auto;\n" +
                    "}\n" +
                    "\n" +
                    ".product-table {\n" +
                    "    border: 1px solid #ddd;\n" +
                    "}\n" +
                    "\n" +
                    ".product-table th, .product-table td {\n" +
                    "    text-align: left;\n" +
                    "}\n" +
                    "\n" +
                    ".total-amount {\n" +
                    "    font-weight: bold;\n" +
                    "}\n" +
                    "\n" +
                    "/* Add more styles as needed */\n");
            htmlContentBuilder.append("</style>");
            htmlContentBuilder.append("<body>");

            //Các nội dung của html
            htmlContentBuilder.append("<h1>").append("LEOPARD STUDIO").append("</h1>");

            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            // Thêm thông tin đơn hàng
            Date ngayTao = hoaDon.getNgayTao();
            String formattedTienGiam;
            KhuyenMai km = hoaDon.getKhuyenMai();

            // Kiểm tra nếu khuyến mãi là null hoặc không có id
            if (km == null || km.getId() == null) {
                formattedTienGiam = "0 VNĐ";
            } else {
                BigDecimal tienGiamToiDa = BigDecimal.valueOf(km.getGiaTriToiThieu());

                BigDecimal tienGiamHoaDon = hoaDon.getTien_giam();

                if (tienGiamHoaDon == null) {
                    formattedTienGiam = "0 VNĐ";
                } else if (tienGiamHoaDon.compareTo(tienGiamToiDa) >= 0) {
                    formattedTienGiam = numberFormat.format(tienGiamHoaDon);
                } else {
                    formattedTienGiam = tienGiamHoaDon + "%";
                }
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedNgayTao = dateFormat.format(ngayTao);

            htmlContentBuilder.append("<h3>").append("Thông tin đơn hàng").append("</h1>");
            htmlContentBuilder.append("<p>Mã đơn hàng: ").append(hoaDon.getMaDon()).append("</p>");
            htmlContentBuilder.append("<p>Ngày mua: ").append(formattedNgayTao).append("</p>");
            htmlContentBuilder.append("<p>Khách hàng: ").append(hoaDon.getNguoiNhan()).append("</p>");
            htmlContentBuilder.append("<p>Số điện thoại khách hàng: ").append(hoaDon.getSdtNguoiNhan()).append("</p>");
            htmlContentBuilder.append("<p>Trạng thái đơn: Đã thanh toán</p>");
            htmlContentBuilder.append("<p>Nhân viên bán hàng: ").append(hoaDon.getNguoiDung().getTenNguoiDung()).append("</p>");


            String formattedTongTienDonHang = numberFormat.format(hoaDon.getTongTienDonHang());
            String formattedTongTienHoaDon = numberFormat.format(hoaDon.getTongTienHoaDon());
            // Thêm chi tiết đơn hàng
            htmlContentBuilder.append("<h3>").append("Chi tiết đơn hàng").append("</h1>");
            htmlContentBuilder.append("<table>");
            htmlContentBuilder.append("<tr><th>Sản phẩm</th><th>Số lượng</th><th>Thành tiền</th></tr>");
            for (HoaDonChiTiet hoaDonChiTiet : hoaDon.getHoaDonChiTiets()) {
                NumberFormat fomatTien = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                String fomatTienSanPham = fomatTien.format(hoaDonChiTiet.getTongTien());
                htmlContentBuilder.append("<tr>");
                htmlContentBuilder.append("<td>").append(hoaDonChiTiet.getSanPhamChiTiet().getSanPham().getTenSanPham())
                        .append(" (").append(hoaDonChiTiet.getSanPhamChiTiet().getKichCo().getTenKichCo())
                        .append("/").append(hoaDonChiTiet.getSanPhamChiTiet().getMauSac().getTenMauSac()).append(")")
                        .append("</td>");

                htmlContentBuilder.append("<td>").append(hoaDonChiTiet.getSoLuong()).append("</td>");
                htmlContentBuilder.append("<td>").append(fomatTienSanPham).append("</td>");
                htmlContentBuilder.append("</tr>");
            }
            htmlContentBuilder.append("</table>");

            // Thêm tổng tiền và các thông tin khác của hóa đơn nếu cần
            htmlContentBuilder.append("<p>Tổng giá trị đơn hàng: ").append(formattedTongTienDonHang).append("</p>");
            htmlContentBuilder.append("<p>Tiền giảm: ").append(formattedTienGiam).append("</p>");
            htmlContentBuilder.append("<p>Tổng tiền thanh toán: ").append(formattedTongTienHoaDon).append("</p>");

            htmlContentBuilder.append("<h3>Xin chân thành cảm ơn sự ủng hộ của bạn dành cho LEOPARD STUDIO!</h3>");
            htmlContentBuilder.append("</body></html>");

            // Gọi phương thức tạo file PDF từ nội dung HTML, sử dụng thư viện iText
            byte[] pdfBytes = createPdfFromHtml(htmlContentBuilder);

            // Lưu file PDF vào thư mục dự án
            String projectRootPath = System.getProperty("user.dir");

            // Tạo đường dẫn động đến thư mục lưu pdf
            String filePath = projectRootPath + "/hoaDonDatt/hoa_don_" + hoaDonId + ".pdf";
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                fileOutputStream.write(pdfBytes);
            } catch (IOException e) {
                e.printStackTrace();
                // Xử lý lỗi nếu cần thiết
            }

            // Thiết lập thông tin phản hồi
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "hoa_don.pdf");

            // Trả về file PDF dưới dạng byte[]
            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<byte[]> generatePdfDonDatHang(Long hoaDonId) {
        Optional<HoaDon> optHoaDon = hoaDonRepository.findById(hoaDonId);
        if (optHoaDon.isPresent()) {
            HoaDon hoaDon = optHoaDon.get();
            // Tạo nội dung HTML cho hóa đơn (thay đổi cho phù hợp với mẫu HTML của bạn)
            StringBuilder htmlContentBuilder = new StringBuilder();
            htmlContentBuilder.append("<html><head>");
            htmlContentBuilder.append("<meta charset=\"UTF-8\">");
            htmlContentBuilder.append("<title>Hóa đơn</title>");
            htmlContentBuilder.append("<style>");
            htmlContentBuilder.append("body {\n" +
                    "    font-family: Arial, sans-serif;\n" +
                    "    line-height: 1.6;\n" +
                    "    background-color: #f9f9f9;\n" +
                    "    padding: 20px;\n" +
                    "}\n" +
                    "\n" +
                    "h1 {\n" +
                    "    color: #338dbc;" +
                    "    text-align: center;\n" +
                    "    font-size: 24px;\n" +
                    "    margin-bottom: 10px;\n" +
                    "}\n" +
                    "\n" +
                    "p {\n" +
                    "    margin-bottom: 10px;\n" +
                    "    color: #333;" +
                    "}\n" +
                    "\n" +
                    "h3 {\n" +
                    "    margin-bottom: 10px;\n" +
                    "    color: #333;\n" +
                    "    text-align: center;" +
                    "}\n" +
                    "\n" +
                    "table {\n" +
                    "    width: 100%;\n" +
                    "    border-collapse: collapse;\n" +
                    "    margin-top: 20px;\n" +
                    "    margin-bottom: 30px;\n" +
                    "}\n" +
                    "\n" +
                    "th, td {\n" +
                    "    padding: 12px 15px;\n" +
                    "    border-bottom: 1px solid #ddd;\n" +
                    "}\n" +
                    "\n" +
                    "th {\n" +
                    "    background-color: #f2f2f2;\n" +
                    "}\n" +
                    "\n" +
                    "tr:hover {\n" +
                    "    background-color: #f5f5f5;\n" +
                    "}\n" +
                    "\n" +
                    "h1.order-details-title {\n" +
                    "    margin-top: 40px;\n" +
                    "}\n" +
                    "\n" +
                    "p.footer-text {\n" +
                    "    margin-top: 30px;\n" +
                    "    text-align: center;\n" +
                    "    color: #888;\n" +
                    "}\n" +
                    "\n" +
                    ".container {\n" +
                    "    max-width: 600px;\n" +
                    "    margin: 0 auto;\n" +
                    "}\n" +
                    "\n" +
                    ".header {\n" +
                    "    text-align: center;\n" +
                    "    margin-bottom: 30px;\n" +
                    "}\n" +
                    "\n" +
                    ".footer {\n" +
                    "    text-align: center;\n" +
                    "    margin-top: 50px;\n" +
                    "    padding-top: 20px;\n" +
                    "    border-top: 1px solid #ddd;\n" +
                    "    color: #888;\n" +
                    "}\n" +
                    "\n" +
                    ".logo {\n" +
                    "    width: 100px;\n" +
                    "    height: auto;\n" +
                    "}\n" +
                    "\n" +
                    ".product-table {\n" +
                    "    border: 1px solid #ddd;\n" +
                    "}\n" +
                    "\n" +
                    ".product-table th, .product-table td {\n" +
                    "    text-align: left;\n" +
                    "}\n" +
                    "\n" +
                    ".total-amount {\n" +
                    "    font-weight: bold;\n" +
                    "}\n" +
                    "\n" +
                    "/* Add more styles as needed */\n");
            htmlContentBuilder.append("</style>");
            htmlContentBuilder.append("<body>");

            //Các nội dung của html
            htmlContentBuilder.append("<h1>").append("LEOPARD STUDIO").append("</h1>");

            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            // Thêm thông tin đơn hàng
            Date ngayTao = hoaDon.getNgayTao();
            String formattedTienGiam;
            KhuyenMai km = hoaDon.getKhuyenMai();

            // Kiểm tra nếu khuyến mãi là null hoặc không có id
            if (km == null || km.getId() == null) {
                formattedTienGiam = "0 VNĐ";
            } else {
                BigDecimal tienGiamToiDa = BigDecimal.valueOf(km.getGiaTriToiThieu());

                BigDecimal tienGiamHoaDon = hoaDon.getTien_giam();

                if (tienGiamHoaDon == null) {
                    formattedTienGiam = "0 VNĐ";
                } else if (tienGiamHoaDon.compareTo(tienGiamToiDa) >= 0) {
                    formattedTienGiam = numberFormat.format(tienGiamHoaDon);
                } else {
                    formattedTienGiam = tienGiamHoaDon + "%";
                }
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedNgayTao = dateFormat.format(ngayTao);

            htmlContentBuilder.append("<h3>").append("Thông tin đơn hàng").append("</h1>");
            htmlContentBuilder.append("<p>Mã đơn hàng: ").append(hoaDon.getMaDon()).append("</p>");
            htmlContentBuilder.append("<p>Ngày mua: ").append(formattedNgayTao).append("</p>");
            htmlContentBuilder.append("<p>Khách hàng: ").append(hoaDon.getNguoiNhan()).append("</p>");
            htmlContentBuilder.append("<p>Số điện thoại khách hàng: ").append(hoaDon.getSdtNguoiNhan()).append("</p>");
            htmlContentBuilder.append("<p>Trạng thái đơn: Đã thanh toán</p>");

            String formattedTongTienDonHang = numberFormat.format(hoaDon.getTongTienDonHang());
            String formattedTongTienHoaDon = numberFormat.format(hoaDon.getTongTienHoaDon());
            // Thêm chi tiết đơn hàng
            htmlContentBuilder.append("<h3>").append("Chi tiết đơn hàng").append("</h1>");
            htmlContentBuilder.append("<table>");
            htmlContentBuilder.append("<tr><th>Sản phẩm</th><th>Số lượng</th><th>Thành tiền</th></tr>");
            for (HoaDonChiTiet hoaDonChiTiet : hoaDon.getHoaDonChiTiets()) {
                NumberFormat fomatTien = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                String fomatTienSanPham = fomatTien.format(hoaDonChiTiet.getTongTien());
                htmlContentBuilder.append("<tr>");
                htmlContentBuilder.append("<td>").append(hoaDonChiTiet.getSanPhamChiTiet().getSanPham().getTenSanPham())
                        .append(" (").append(hoaDonChiTiet.getSanPhamChiTiet().getKichCo().getTenKichCo())
                        .append("/").append(hoaDonChiTiet.getSanPhamChiTiet().getMauSac().getTenMauSac()).append(")")
                        .append("</td>");

                htmlContentBuilder.append("<td>").append(hoaDonChiTiet.getSoLuong()).append("</td>");
                htmlContentBuilder.append("<td>").append(fomatTienSanPham).append("</td>");
                htmlContentBuilder.append("</tr>");
            }
            htmlContentBuilder.append("</table>");

            // Thêm tổng tiền và các thông tin khác của hóa đơn nếu cần
            htmlContentBuilder.append("<p>Tổng giá trị đơn hàng: ").append(formattedTongTienDonHang).append("</p>");
            htmlContentBuilder.append("<p>Tiền giảm: ").append(formattedTienGiam).append("</p>");
            htmlContentBuilder.append("<p>Tổng tiền thanh toán: ").append(formattedTongTienHoaDon).append("</p>");

            htmlContentBuilder.append("<h3>Xin chân thành cảm ơn sự ủng hộ của bạn dành cho LEOPARD STUDIO!</h3>");
            htmlContentBuilder.append("</body></html>");

            // Gọi phương thức tạo file PDF từ nội dung HTML, sử dụng thư viện iText
            byte[] pdfBytes = createPdfFromHtml(htmlContentBuilder);

            // Lưu file PDF vào thư mục dự án
            String projectRootPath = System.getProperty("user.dir");

            // Tạo đường dẫn động đến thư mục lưu pdf
            String filePath = projectRootPath + "/hoaDonDatt/hoa_don_" + hoaDonId + ".pdf";
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                fileOutputStream.write(pdfBytes);
            } catch (IOException e) {
                e.printStackTrace();
                // Xử lý lỗi nếu cần thiết
            }

            // Thiết lập thông tin phản hồi
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "hoa_don.pdf");

            // Trả về file PDF dưới dạng byte[]
            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        }
        return ResponseEntity.notFound().build();
    }


    // Phương thức tạo file PDF từ nội dung HTML sử dụng thư viện iText
    private byte[] createPdfFromHtml(StringBuilder htmlContent) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ConverterProperties converterProperties = new ConverterProperties();
            HtmlConverter.convertToPdf(htmlContent.toString(), outputStream, converterProperties);
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
