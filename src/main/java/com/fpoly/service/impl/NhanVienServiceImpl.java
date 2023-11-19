package com.fpoly.service.impl;

import com.fpoly.config.BcryptedPasswordEncoderConfig;
import com.fpoly.entity.NguoiDung;
import com.fpoly.entity.NguoiDungVaiTro;
import com.fpoly.entity.VaiTro;
import com.fpoly.repository.NguoiDungRepository;
import com.fpoly.repository.NguoiDungVaiTroRepository;
import com.fpoly.repository.VaiTroRepository;
import com.fpoly.service.MailService;
import com.fpoly.service.NhanVienService;
import com.fpoly.util.RanDomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class NhanVienServiceImpl implements NhanVienService {
    @Autowired
    NguoiDungRepository nguoiDungRepository;

    @Autowired
    MailService mailService;

    @Autowired
    private BcryptedPasswordEncoderConfig passwordEncoder;

    @Autowired
    NguoiDungVaiTroRepository nguoiDungVaiTroRepository;

    @Autowired
    VaiTroRepository vaiTroRepository;

    @Override
    public Map<String, Object> themMoiNhanVien(String email, String diaChi, String soDienThoai, String ho, String ten, String anhNhanVien, long ChucVu, Map<String, Object> response) {
        char[] password = RanDomUtil.randomFull();

        // Kiểm tra dữ liệu rỗng
        if (ho.isEmpty() || ten.isEmpty() || email.isEmpty() || diaChi.isEmpty() || soDienThoai.isEmpty()) {
            response.put("warning", true);
            response.put("error", "Vui lòng nhập đầy đủ thông tin");
            return response;
        }

        // Kiểm tra trùng email
        NguoiDung existingNguoiDungByEmail = nguoiDungRepository.findByEmail(email);
        if (existingNguoiDungByEmail != null) {
            response.put("warning", true);
            response.put("error", "Email đã tồn tại");
            return response;
        }

        // Kiểm tra trùng số điện thoại
        NguoiDung existingNguoiDungBySoDienThoai = nguoiDungRepository.findBysoDienThoai(soDienThoai);
        if (existingNguoiDungBySoDienThoai != null) {
            response.put("warning", true);
            response.put("error", "Số điện thoại đã tồn tại");
            return response;
        }

        Integer maxId = nguoiDungRepository.getMaxId();
        int id;
        String ma;

        if (maxId != null) {
            id = maxId + 1;
            ma = "NV" + id;
        } else {
            id = 1;
            ma = "NV" + id;
        }

        // Lưu mã người dùng vào đối tượng nguoiDung
        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setMaNguoiDung(ma);
        nguoiDung.setEmail(email);
        nguoiDung.setDiaChi(diaChi);
        nguoiDung.setSoDienThoai(soDienThoai);
        nguoiDung.setTenNguoiDung(ho + " " + ten);
        nguoiDung.setDaXoa(false);
        nguoiDung.setAnhNhanVien(anhNhanVien);
        nguoiDung.setMatKhau(passwordEncoder.encode(new String(password)));

        mailService.sendMail("vuongnqph25621@fpt.edu.vn",
                nguoiDung.getEmail(),
                "Bạn đã đăng ký tài khoản thành công !",
                "Họ tên  : " + nguoiDung.getTenNguoiDung() + "\n" +
                        "Số điện thoại  :" + nguoiDung.getSoDienThoai()
                        + "Mật khẩu : " + new String(password));

        // Kiểm tra xem đã lưu thành công vào cơ sở dữ liệu hay chưa
        NguoiDung savedNguoiDung = nguoiDungRepository.save(nguoiDung);

        if (savedNguoiDung != null) {
            Optional<VaiTro> optVaiTro = vaiTroRepository.findById(ChucVu);
            if (optVaiTro.isPresent()) {
                VaiTro vaiTro = optVaiTro.get();
                NguoiDungVaiTro nguoiDungVaiTro = new NguoiDungVaiTro();
                nguoiDungVaiTro.setVaiTro(vaiTro);
                nguoiDungVaiTro.setNguoiDung(nguoiDung);
                nguoiDungVaiTroRepository.save(nguoiDungVaiTro);
            }
            response.put("warning", false);
            response.put("success", true);
        } else {
            response.put("warning", true);
            response.put("error", "Lỗi khi lưu người dùng vào cơ sở dữ liệu");
        }

        return response;
    }


    @Override
    public Map<String, Object> ChinhSuaNhanVien(Long idNhanVien, String email, String diaChi, String soDienThoai, String hoTen, String anhNhanVien, long ChucVu, Map<String, Object> response) {
        Optional<NguoiDung> optionalNguoiDung = nguoiDungRepository.findById(idNhanVien);

        if (!optionalNguoiDung.isPresent()) {
            response.put("success", false);
            response.put("error", "Không tìm thấy người dùng với ID: " + idNhanVien);
            return response;
        }

        NguoiDung nguoiDung = optionalNguoiDung.get();

        // Kiểm tra dữ liệu rỗng
        if (hoTen.isEmpty() || email.isEmpty() || diaChi.isEmpty() || soDienThoai.isEmpty()) {
            response.put("success", false);
            response.put("error", "Vui lòng nhập đầy đủ thông tin");
            return response;
        }

        // Kiểm tra trùng email
        NguoiDung existingNguoiDungByEmail = nguoiDungRepository.findByEmail(email);
        if (existingNguoiDungByEmail != null && !existingNguoiDungByEmail.getId().equals(nguoiDung.getId())) {
            response.put("success", false);
            response.put("error", "Email đã tồn tại");
            return response;
        }

        // Kiểm tra trùng số điện thoại
        NguoiDung existingNguoiDungBySoDienThoai = nguoiDungRepository.findBysoDienThoai(soDienThoai);
        if (existingNguoiDungBySoDienThoai != null && !existingNguoiDungBySoDienThoai.getId().equals(nguoiDung.getId())) {
            response.put("success", false);
            response.put("error", "Số điện thoại đã tồn tại");
            return response;
        }

        nguoiDung.setEmail(email);
        nguoiDung.setDiaChi(diaChi);
        nguoiDung.setSoDienThoai(soDienThoai);
        nguoiDung.setTenNguoiDung(hoTen);
        nguoiDung.setAnhNhanVien(anhNhanVien);

        // Thay đổi mật khẩu nếu cần
        // nguoiDung.setMatKhau(passwordEncoder.encode("new_password"));

        nguoiDungRepository.save(nguoiDung);

        Optional<VaiTro> optVaiTro = vaiTroRepository.findById(ChucVu);
        if (optVaiTro.isPresent()) {
            VaiTro vaiTro = optVaiTro.get();
            NguoiDungVaiTro nguoiDungVaiTro = nguoiDungVaiTroRepository.findByNguoiDungId(nguoiDung.getId());
            nguoiDungVaiTro.setVaiTro(vaiTro);
            nguoiDungVaiTroRepository.save(nguoiDungVaiTro);
        }

        response.put("success", true);
        response.put("message", "Chỉnh sửa thông tin nhân viên thành công");

        return response;
    }

    @Override
    public ResponseEntity<String> XoaNhanVien(Long id) {
        Optional<NguoiDung> optionalNguoiDung = nguoiDungRepository.findById(id);
        if (optionalNguoiDung.isPresent()) {
            NguoiDung nguoiDung = optionalNguoiDung.get();
            nguoiDung.setDaXoa(true);
            nguoiDungRepository.save(nguoiDung);
            String message = "Xóa thành công thành công";
            return ResponseEntity.ok(message);
        } else {
            String errorMessage = "Không tìm thấy người dùng";
            return ResponseEntity.notFound().build();
        }
    }
}
