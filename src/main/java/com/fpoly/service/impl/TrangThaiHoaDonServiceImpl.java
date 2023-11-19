package com.fpoly.service.impl;

import com.fpoly.dto.HoaDonDTO;
import com.fpoly.entity.GiaoDich;
import com.fpoly.entity.HoaDon;
import com.fpoly.repository.HoaDonRepoditory2;
import com.fpoly.service.TrangThaiHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrangThaiHoaDonServiceImpl implements TrangThaiHoaDonService {
    @Autowired
    HoaDonRepoditory2 hoaDonRepoditory2;

    private List<HoaDonDTO> convertPageToList(Page<HoaDon> page) {
        List<HoaDonDTO> listHoaDonDTO = new ArrayList<>();
        GiaoDich gd = new GiaoDich();
        for (HoaDon hoaDon : page.getContent()) {
            HoaDonDTO hoaDonDTO = new HoaDonDTO();
            hoaDonDTO.setMaDon(hoaDon.getMaDon());
            hoaDonDTO.setNguoiNhan(hoaDon.getNguoiNhan());
            hoaDonDTO.setSdtNguoiNhan(hoaDon.getSdtNguoiNhan());
            hoaDonDTO.setGhiChu(hoaDon.getGhiChu());
            hoaDonDTO.setTongTienHoaDon(hoaDon.getTongTienHoaDon());
            hoaDonDTO.setTienShip(hoaDon.getTienShip());
            hoaDonDTO.setTongTienDonHang(hoaDon.getTongTienDonHang());
            hoaDonDTO.setLoaiDonHang(hoaDon.getLoaiHoaDon());
            hoaDonDTO.setMaDonHang(hoaDon.getMaDon());
            hoaDonDTO.setPage(page.getNumber() + 1);
            hoaDonDTO.setTotalPages(page.getTotalPages());
            hoaDonDTO.setTrangThaiId(hoaDon.getTrangThai().getId());
            hoaDonDTO.setNgayTao(hoaDon.getNgayTao());
            hoaDonDTO.setId(hoaDon.getId());

            listHoaDonDTO.add(hoaDonDTO);
        }
        return listHoaDonDTO;
    }

    //Chờ giao hàng
    @Override
    public void getHoaDonChoLayHang(Model model, int page, int size) {
        HoaDonDTO dto = new HoaDonDTO();
        dto.setLimit(size);
        PageRequest pageRequest = PageRequest.of(page - 1, dto.getLimit(), Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<HoaDon> ChoGiaoHang = hoaDonRepoditory2.findByTrangThaiHoaDonListTrangThai(2, pageRequest);
        List<HoaDonDTO> listHoaDonDTO = convertPageToList(ChoGiaoHang);
        dto.setListHoaDonDTO(listHoaDonDTO);
        model.addAttribute("HoaDonDTO", dto);
        model.addAttribute("pageChoGiaoHang", ChoGiaoHang.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
    }

    @Override
    public void timKiemHoaDonChoGiaoHang(Model model, int page, int size, String duLieuTimKiem) {
        HoaDonDTO dto = new HoaDonDTO();
        dto.setLimit(size);
        dto.setDuLieuTimKiem(duLieuTimKiem);
        PageRequest pageRequest = PageRequest.of(page - 1, dto.getLimit(), Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<HoaDon> ChoGiaoHang = hoaDonRepoditory2.timKiemHoaDonDatHang(2, duLieuTimKiem, pageRequest);
        List<HoaDonDTO> listHoaDonDTO = convertPageToList(ChoGiaoHang);
        dto.setListHoaDonDTO(listHoaDonDTO);
        model.addAttribute("HoaDonDTO", dto);
        model.addAttribute("duLieuTimKiem", duLieuTimKiem);
        model.addAttribute("pageChoGiaoHang", ChoGiaoHang.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
    }

    @Override
    public void timKiemHoaDonTheoNgayChoGiaoHang(Model model, int page, int size, String duLieuTimKiemString) {
        HoaDonDTO dto = new HoaDonDTO();
        dto.setLimit(size);
        LocalDate duLieuTimKiem = LocalDate.parse(duLieuTimKiemString);
        dto.setDuLieuTimKiem(duLieuTimKiemString);
        PageRequest pageRequest = PageRequest.of(page - 1, dto.getLimit(), Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<HoaDon> ChoGiaoHang = hoaDonRepoditory2.timKiemHoaDonTheoNgay(2, duLieuTimKiem, pageRequest);
        List<HoaDonDTO> listHoaDonDTO = convertPageToList(ChoGiaoHang);
        dto.setListHoaDonDTO(listHoaDonDTO);
        model.addAttribute("HoaDonDTO", dto);
        model.addAttribute("duLieuTimKiem", duLieuTimKiem);
        model.addAttribute("pageChoGiaoHang", ChoGiaoHang.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
    }

    //Chờ xác nhận
    @Override
    public void getHoaDonChoXacNhan(Model model, int page, int size) {
        HoaDonDTO dto = new HoaDonDTO();
        dto.setLimit(size);
        PageRequest pageRequest = PageRequest.of(page - 1, dto.getLimit(), Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<HoaDon> choXacNhan = hoaDonRepoditory2.findByTrangThaiHoaDonListTrangThai(1, pageRequest);
        List<HoaDonDTO> listHoaDonDTO = convertPageToList(choXacNhan);
        dto.setListHoaDonDTO(listHoaDonDTO);
        model.addAttribute("HoaDonDTO", dto);
        model.addAttribute("pageChoXacNhan", choXacNhan.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
    }

    @Override
    public void timKiemHoaDonChoXacNhanDonHang(Model model, int page, int size, String duLieuTimKiem) {
        HoaDonDTO dto = new HoaDonDTO();
        dto.setLimit(size);
        dto.setDuLieuTimKiem(duLieuTimKiem);
        PageRequest pageRequest = PageRequest.of(page - 1, dto.getLimit(), Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<HoaDon> ChoGiaoHang = hoaDonRepoditory2.timKiemHoaDonDatHang(1, duLieuTimKiem, pageRequest);
        List<HoaDonDTO> listHoaDonDTO = convertPageToList(ChoGiaoHang);
        dto.setListHoaDonDTO(listHoaDonDTO);
        model.addAttribute("HoaDonDTO", dto);
        model.addAttribute("duLieuTimKiem", duLieuTimKiem);
        model.addAttribute("pageChoXacNhan", ChoGiaoHang.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
    }

    @Override
    public void timKiemHoaDonTheoNgayChoXacNhan(Model model, int page, int size, String duLieuTimKiemString) {
        HoaDonDTO dto = new HoaDonDTO();
        dto.setLimit(size);
        LocalDate duLieuTimKiem = LocalDate.parse(duLieuTimKiemString);
        dto.setDuLieuTimKiem(duLieuTimKiemString);
        PageRequest pageRequest = PageRequest.of(page - 1, dto.getLimit(), Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<HoaDon> ChoGiaoHang = hoaDonRepoditory2.timKiemHoaDonTheoNgay(1, duLieuTimKiem, pageRequest);
        List<HoaDonDTO> listHoaDonDTO = convertPageToList(ChoGiaoHang);
        dto.setListHoaDonDTO(listHoaDonDTO);
        model.addAttribute("HoaDonDTO", dto);
        model.addAttribute("duLieuTimKiem", duLieuTimKiem);
        model.addAttribute("pageChoXacNhan", ChoGiaoHang.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
    }

    //Đã giao hàng
    @Override
    public void getHoaDonDaGiao(Model model, int page, int size) {
        HoaDonDTO dto = new HoaDonDTO();
        dto.setLimit(size);
        PageRequest pageRequest = PageRequest.of(page - 1, dto.getLimit(), Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<HoaDon> DaGiao = hoaDonRepoditory2.findByTrangThaiHoaDonListTrangThai(4, pageRequest);
        List<HoaDonDTO> listHoaDonDTO = convertPageToList(DaGiao);
        dto.setListHoaDonDTO(listHoaDonDTO);
        model.addAttribute("HoaDonDTO", dto);
        model.addAttribute("pageDaGiao", DaGiao.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
    }

    @Override
    public void timKiemHoaDonDaGiaoHang(Model model, int page, int size, String duLieuTimKiem) {
        HoaDonDTO dto = new HoaDonDTO();
        dto.setLimit(size);
        dto.setDuLieuTimKiem(duLieuTimKiem);
        PageRequest pageRequest = PageRequest.of(page - 1, dto.getLimit(), Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<HoaDon> daGiao = hoaDonRepoditory2.timKiemHoaDonDatHang(4, duLieuTimKiem, pageRequest);
        List<HoaDonDTO> listHoaDonDTO = convertPageToList(daGiao);
        dto.setListHoaDonDTO(listHoaDonDTO);
        model.addAttribute("HoaDonDTO", dto);
        model.addAttribute("duLieuTimKiem", duLieuTimKiem);
        model.addAttribute("pageDaGiao", daGiao.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
    }

    @Override
    public void timKiemHoaDonTheoNgayDaGiaoHang(Model model, int page, int size, String duLieuTimKiemString) {
        HoaDonDTO dto = new HoaDonDTO();
        dto.setLimit(size);
        LocalDate duLieuTimKiem = LocalDate.parse(duLieuTimKiemString);
        dto.setDuLieuTimKiem(duLieuTimKiemString);
        PageRequest pageRequest = PageRequest.of(page - 1, dto.getLimit(), Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<HoaDon> daGiao = hoaDonRepoditory2.timKiemHoaDonTheoNgay(4, duLieuTimKiem, pageRequest);
        List<HoaDonDTO> listHoaDonDTO = convertPageToList(daGiao);
        dto.setListHoaDonDTO(listHoaDonDTO);
        model.addAttribute("HoaDonDTO", dto);
        model.addAttribute("duLieuTimKiem", duLieuTimKiem);
        model.addAttribute("pageDaGiao", daGiao.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
    }

    //Đã hủy
    @Override
    public void getHoaDonDaHuy(Model model, int page, int size) {
        HoaDonDTO dto = new HoaDonDTO();
        dto.setLimit(size);
        PageRequest pageRequest = PageRequest.of(page - 1, dto.getLimit(), Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<HoaDon> DaHuy = hoaDonRepoditory2.findByTrangThaiHoaDonListTrangThai(5, pageRequest);
        List<HoaDonDTO> listHoaDonDTO = convertPageToList(DaHuy);
        dto.setListHoaDonDTO(listHoaDonDTO);
        model.addAttribute("HoaDonDTO", dto);
        model.addAttribute("pageDaHuy", DaHuy.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
    }

    @Override
    public void timKiemHoaDonDaHuy(Model model, int page, int size, String duLieuTimKiem) {
        HoaDonDTO dto = new HoaDonDTO();
        dto.setLimit(size);
        dto.setDuLieuTimKiem(duLieuTimKiem);
        PageRequest pageRequest = PageRequest.of(page - 1, dto.getLimit(), Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<HoaDon> DaHuy = hoaDonRepoditory2.timKiemHoaDonDatHang(5, duLieuTimKiem, pageRequest);
        List<HoaDonDTO> listHoaDonDTO = convertPageToList(DaHuy);
        dto.setListHoaDonDTO(listHoaDonDTO);
        model.addAttribute("HoaDonDTO", dto);
        model.addAttribute("duLieuTimKiem", duLieuTimKiem);
        model.addAttribute("pageDaHuy", DaHuy.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
    }

    @Override
    public void timKiemHoaDonTheoNgayDaHuy(Model model, int page, int size, String duLieuTimKiemString) {
        HoaDonDTO dto = new HoaDonDTO();
        dto.setLimit(size);
        LocalDate duLieuTimKiem = LocalDate.parse(duLieuTimKiemString);
        dto.setDuLieuTimKiem(duLieuTimKiemString);
        PageRequest pageRequest = PageRequest.of(page - 1, dto.getLimit(), Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<HoaDon> DaHuy = hoaDonRepoditory2.timKiemHoaDonTheoNgay(5, duLieuTimKiem, pageRequest);
        List<HoaDonDTO> listHoaDonDTO = convertPageToList(DaHuy);
        dto.setListHoaDonDTO(listHoaDonDTO);
        model.addAttribute("HoaDonDTO", dto);
        model.addAttribute("duLieuTimKiem", duLieuTimKiem);
        model.addAttribute("pageDaHuy", DaHuy.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
    }

    //Đang giao
    @Override
    public void getHoaDonDangGiao(Model model, int page, int size) {
        HoaDonDTO dto = new HoaDonDTO();
        dto.setLimit(size);
        PageRequest pageRequest = PageRequest.of(page - 1, dto.getLimit(), Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<HoaDon> dangtGiao = hoaDonRepoditory2.findByTrangThaiHoaDonListTrangThai(3, pageRequest);
        List<HoaDonDTO> listHoaDonDTO = convertPageToList(dangtGiao);
        dto.setListHoaDonDTO(listHoaDonDTO);

        model.addAttribute("HoaDonDTO", dto);
        model.addAttribute("pageDangGiao", dangtGiao.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
    }

    @Override
    public void timKiemHoaDonDangGiao(Model model, int page, int size, String duLieuTimKiem) {
        HoaDonDTO dto = new HoaDonDTO();
        dto.setLimit(size);
        dto.setDuLieuTimKiem(duLieuTimKiem);
        PageRequest pageRequest = PageRequest.of(page - 1, dto.getLimit(), Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<HoaDon> ChoGiaoHang = hoaDonRepoditory2.timKiemHoaDonDatHang(3, duLieuTimKiem, pageRequest);
        List<HoaDonDTO> listHoaDonDTO = convertPageToList(ChoGiaoHang);
        dto.setListHoaDonDTO(listHoaDonDTO);
        model.addAttribute("HoaDonDTO", dto);
        model.addAttribute("duLieuTimKiem", duLieuTimKiem);
        model.addAttribute("pageDangGiao", ChoGiaoHang.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
    }

    @Override
    public void timKiemHoaDonTheoNgayDangGiao(Model model, int page, int size, String duLieuTimKiemString) {
        HoaDonDTO dto = new HoaDonDTO();
        dto.setLimit(size);
        LocalDate duLieuTimKiem = LocalDate.parse(duLieuTimKiemString);
        dto.setDuLieuTimKiem(duLieuTimKiemString);
        PageRequest pageRequest = PageRequest.of(page - 1, dto.getLimit(), Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<HoaDon> ChoGiaoHang = hoaDonRepoditory2.timKiemHoaDonTheoNgay(3, duLieuTimKiem, pageRequest);
        List<HoaDonDTO> listHoaDonDTO = convertPageToList(ChoGiaoHang);
        dto.setListHoaDonDTO(listHoaDonDTO);
        model.addAttribute("HoaDonDTO", dto);
        model.addAttribute("duLieuTimKiem", duLieuTimKiem);
        model.addAttribute("pageDangGiao", ChoGiaoHang.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
    }

}
