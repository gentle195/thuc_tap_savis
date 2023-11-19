package com.fpoly.controller.admin.NhanVien;

import com.fpoly.entity.NguoiDung;
import com.fpoly.entity.NguoiDungVaiTro;
import com.fpoly.repository.NguoiDungRepository;
import com.fpoly.repository.NguoiDungVaiTroRepository;
import com.fpoly.security.NguoiDungDetails;
import com.fpoly.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class ThongTinNhanVienController {
    @Autowired
    NguoiDungRepository nguoiDungRepository;

    @Autowired
    NguoiDungVaiTroRepository nguoiDungVaiTroRepository;

    @RequestMapping("admin/thongTinNhanVienDangDangNhap")
    public String thongTinNhanVienDangDangNhap(Model model) {
        NguoiDungDetails nd = SecurityUtil.getPrincipal();
        String maNguoiDung = nd.getMaNguoiDung();
        NguoiDung nguoiDung = nguoiDungRepository.findNguoiDungByMaNguoiDung(maNguoiDung);
        NguoiDungVaiTro phanQuyen = nguoiDungVaiTroRepository.findByNguoiDungId(nguoiDung.getId());
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("phanQuyen", phanQuyen);
        return "admin/NhanVien/ThongTinNhanVien/thongTinNhanVien";
    }

    @RequestMapping("admin/thongTinNhanVien/MaNhanVien={maNhanVien}")
    public String thongTinNhanVien(@PathVariable("maNhanVien") String maNhanVien, Model model) {
        NguoiDung nguoiDung = nguoiDungRepository.findNguoiDungByMaNguoiDung(maNhanVien);
        NguoiDungVaiTro phanQuyen = nguoiDungVaiTroRepository.findByNguoiDungId(nguoiDung.getId());
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("phanQuyen", phanQuyen);
        return "admin/NhanVien/ThongTinNhanVien/thongTinNhanVien";
    }
}
