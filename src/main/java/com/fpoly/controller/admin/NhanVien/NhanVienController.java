package com.fpoly.controller.admin.NhanVien;

import com.fpoly.dto.NguoiDungDTO;
import com.fpoly.entity.*;
import com.fpoly.repository.NguoiDungRepository;
import com.fpoly.repository.NguoiDungVaiTroRepository;
import com.fpoly.repository.VaiTroRepository;
import com.fpoly.service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
public class NhanVienController {
    @Autowired
    NguoiDungService nguoiDungService;

    @Autowired
    NguoiDungRepository nguoiDungRepository;

    @Autowired
    VaiTroRepository vaiTroRepository;

    @Autowired
    NguoiDungVaiTroRepository nguoiDungVaiTroRepository;

    private List<NguoiDungDTO> convertPageToList(Page<NguoiDung> page) {
        List<NguoiDungDTO> listNguoiDungDTO = new ArrayList<>();
        for (NguoiDung nguoiDung : page.getContent()) {
            NguoiDungDTO nguoiDungDTO = new NguoiDungDTO();
            nguoiDungDTO.setMaNhanVien(nguoiDung.getMaNguoiDung());
            nguoiDungDTO.setTenNguoiDung(nguoiDung.getTenNguoiDung());
            nguoiDungDTO.setEmail(nguoiDung.getEmail());
            nguoiDungDTO.setSoDienThoai(nguoiDung.getSoDienThoai());
            nguoiDungDTO.setNgayTao(nguoiDung.getNgayTao());
            nguoiDungDTO.setTrangThai(nguoiDung.getTrangThai());
            nguoiDungDTO.setId(nguoiDung.getId());
            nguoiDungDTO.setAnhNhanVien(nguoiDung.getAnhNhanVien());

            listNguoiDungDTO.add(nguoiDungDTO);
        }
        return listNguoiDungDTO;
    }

    //List
    @GetMapping("/admin/NhanVien/danhSach")
    public String getUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "4") int size,
            Model model
    ) {
        NguoiDungDTO dto = new NguoiDungDTO();
        PageRequest pageable = PageRequest.of(page - 1, size, Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<NguoiDung> users = nguoiDungRepository.findAllNguoiDung(pageable);
        List<NguoiDungDTO> listNguoiDungDTO = convertPageToList(users);
        dto.setListNguoiDungDTO(listNguoiDungDTO);
        model.addAttribute("NguoiDungDTO", dto);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "admin/NhanVien/danhSachNhanVien";
    }

    //Tìm kiếm all
    @RequestMapping("admin/NhanVien/timKiem/{duLieuTimKiem}")
    public String timKiemNguoiDung(Model model,
                                   @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "4") int size,
                                   @PathVariable("duLieuTimKiem") String duLieuTimKiem) {

        NguoiDungDTO dto = new NguoiDungDTO();
        dto.setDuLieuTimKiem(duLieuTimKiem);
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<NguoiDung> nguoiDung = nguoiDungRepository.timKiemNguoiDung(duLieuTimKiem, pageRequest);
        List<NguoiDungDTO> listNguoiDungDTO = convertPageToList(nguoiDung);
        dto.setListNguoiDungDTO(listNguoiDungDTO);
        model.addAttribute("NguoiDungDTO", dto);
        model.addAttribute("DuLieuTimKiem", duLieuTimKiem);
        model.addAttribute("totalPages", nguoiDung.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "admin/NhanVien/danhSachNhanVien";
    }

    //Tìm kiếm nga tạo
    @RequestMapping("admin/NhanVien/Ngay/{duLieuTimKiem}")
    public String timKiemNguoiDungbyNgayTao(Model model,
                                            @RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "4") int size,
                                            @PathVariable("duLieuTimKiem") String duLieuTimKiemString) {

        NguoiDungDTO dto = new NguoiDungDTO();
        LocalDate duLieuTimKiem = LocalDate.parse(duLieuTimKiemString);
        dto.setDuLieuTimKiem(duLieuTimKiemString);
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<NguoiDung> nguoiDung = nguoiDungRepository.timKiemNguoiDungByNgayTao(duLieuTimKiem, pageRequest);
        List<NguoiDungDTO> listNguoiDungDTO = convertPageToList(nguoiDung);
        dto.setListNguoiDungDTO(listNguoiDungDTO);
        model.addAttribute("DuLieuTimKiem", duLieuTimKiem);
        model.addAttribute("NguoiDungDTO", dto);
        model.addAttribute("totalPages", nguoiDung.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "admin/NhanVien/danhSachNhanVien";
    }

    @RequestMapping("admin/NhanVien/TrangThai/{duLieuTimKiem}")
    public String timKiemNguoiDungbyTrangThai(Model model,
                                            @RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "4") int size,
                                            @PathVariable("duLieuTimKiem") int trangThai) {

        NguoiDungDTO dto = new NguoiDungDTO();

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<NguoiDung> nguoiDung = nguoiDungRepository.timKiemNguoiDungByTrangThai(trangThai, pageRequest);
        List<NguoiDungDTO> listNguoiDungDTO = convertPageToList(nguoiDung);
        dto.setListNguoiDungDTO(listNguoiDungDTO);
        model.addAttribute("DuLieuTimKiem", trangThai);
        model.addAttribute("NguoiDungDTO", dto);
        model.addAttribute("totalPages", nguoiDung.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "admin/NhanVien/danhSachNhanVien";
    }

    //Add
    @RequestMapping("/NhanVien/themMoi")
    public String themNguoiDung(Model model) {
        NguoiDung nguoiDung = new NguoiDung();
        List<VaiTro> vaiTro = vaiTroRepository.findAll();
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("phanQuyen", vaiTro);
        return "admin/NhanVien/themNhanVien";
    }

    //Update
    @RequestMapping("admin/chinhSuaNhanVien/id-nhan-vien={id}")
    public String chinhSuaNguoiDung(@PathVariable("id") Long id, Model model) {
        NguoiDung nguoiDung = nguoiDungService.getNguoiDungById(id);
        List<VaiTro> vaiTro = vaiTroRepository.findAll();
        model.addAttribute("nguoiDungEdit", nguoiDung);
        model.addAttribute("phanQuyen", vaiTro);
        return "admin/NhanVien/chinhSuaNhanVien";
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<String> updateStatus(@RequestParam("userId") Long id, @RequestParam("status") int trangThai) {
        try {
            nguoiDungService.updateUserStatus(id, trangThai);
            return ResponseEntity.ok("Cập nhật trạng thái thành công");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Người dùng không tồn tại");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi cập nhật trạng thái");
        }
    }
}
