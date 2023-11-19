package com.fpoly.service.impl;

import com.fpoly.dto.HoaDonDTO;
import com.fpoly.entity.GiaoDich;
import com.fpoly.entity.HoaDon;
import com.fpoly.entity.HoaDonChiTiet;
import com.fpoly.entity.MauSac;
import com.fpoly.repository.HinhAnhRepository;
import com.fpoly.repository.HoaDonChiTietRepository2;
import com.fpoly.repository.HoaDonRepoditory2;
import com.fpoly.repository.HoaDonRepository;
import com.fpoly.service.DonHangTaiQuayService;
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
public class DonHangTaiQuayServiceImpl implements DonHangTaiQuayService {
    @Autowired
    HoaDonRepoditory2 hoaDonRepoditory2;

    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    HoaDonChiTietRepository2 hoaDonChiTietRepository2;

    @Autowired
    HinhAnhRepository hinhAnhRepository;

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

    //Đã thanh toán
    @Override
    public void DanhSachDaThanhToan(Model model, int page, int size) {
        HoaDonDTO dto = new HoaDonDTO();
        dto.setLimit(size);

        PageRequest pageRequest = PageRequest.of(page - 1, dto.getLimit(), Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<HoaDon> daThanhToan = hoaDonRepoditory2.findHoaDonDaThanhToan(pageRequest);

        List<HoaDonDTO> listHoaDonDTO = convertPageToList(daThanhToan);
        dto.setListHoaDonDTO(listHoaDonDTO);

        model.addAttribute("HoaDonDTO", dto);
        model.addAttribute("pageDaThanhToan", daThanhToan.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
    }

    @Override
    public void timKiemHoaDonDaThanhToan(Model model, int page, int size, String duLieuTimKiem) {
        HoaDonDTO dto = new HoaDonDTO();
        dto.setLimit(size);
        dto.setDuLieuTimKiem(duLieuTimKiem);
        PageRequest pageRequest = PageRequest.of(page - 1, dto.getLimit(), Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<HoaDon> daThanhToan = hoaDonRepoditory2.findAllHoaDonDaThanhToanCoPhanTrang(duLieuTimKiem, pageRequest);

        List<HoaDonDTO> listHoaDonDTO = convertPageToList(daThanhToan);
        dto.setListHoaDonDTO(listHoaDonDTO);
        model.addAttribute("HoaDonDTO", dto);
        model.addAttribute("pageDaThanhToan", daThanhToan.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("duLieuTimKiem", duLieuTimKiem);
        model.addAttribute("size", size);
    }

    @Override
    public void timKiemHoaDonDaThanhToanTheoNgayTao(Model model, int page, int size, String duLieuTimKiemString) {
        HoaDonDTO dto = new HoaDonDTO();
        dto.setLimit(size);

        LocalDate duLieuTimKiem = LocalDate.parse(duLieuTimKiemString);
        dto.setDuLieuTimKiem(duLieuTimKiemString);

        PageRequest pageRequest = PageRequest.of(page - 1, dto.getLimit(), Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<HoaDon> daThanhToan = hoaDonRepoditory2.findHoaDoDaThanhToanByNgayTao(duLieuTimKiem, pageRequest);

        List<HoaDonDTO> listHoaDonDTO = convertPageToList(daThanhToan);
        dto.setListHoaDonDTO(listHoaDonDTO);
        model.addAttribute("HoaDonDTO", dto);
        model.addAttribute("pageDaThanhToan", daThanhToan.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("duLieuTimKiem", duLieuTimKiem);
        model.addAttribute("size", size);
    }

    @Override
    public void ChiTietHoaDonTaiQuayDaThanhToan(Long id, Model model) {
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        // Lấy danh sách ảnh chính của tất cả sản phẩm và lưu vào List
        List<HoaDonChiTiet> hoaDonChiTiet = hoaDonChiTietRepository2.findHDCT(id);
        List<String> tenAnhChinhList = new ArrayList<>();
        for (HoaDonChiTiet hoadonCT : hoaDonChiTiet) {
            MauSac mauSac = hoadonCT.getSanPhamChiTiet().getMauSac();
            Long sanPhamId = hoadonCT.getSanPhamChiTiet().getSanPham().getId();

            String tenAnhChinh = hinhAnhRepository.findTenAnhChinhByMauSacIdAndSanPhamId(mauSac.getId(), sanPhamId);
            tenAnhChinhList.add(tenAnhChinh);
        }
        model.addAttribute("tenAnhChinhList", tenAnhChinhList);

        model.addAttribute("hoaDon", hoaDon);
    }

    //Đã hủy
    @Override
    public void daHuyTaiQuay(Model model, int page, int size) {
        HoaDonDTO dto = new HoaDonDTO();
        dto.setLimit(size);

        PageRequest pageRequest = PageRequest.of(page - 1, dto.getLimit(), Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<HoaDon> daHuy = hoaDonRepoditory2.findHoaDonDaHuy(pageRequest);
        List<HoaDonDTO> listHoaDonDTO = convertPageToList(daHuy);
        dto.setListHoaDonDTO(listHoaDonDTO);
        model.addAttribute("HoaDonDTO", dto);
        model.addAttribute("pagedaHuy", daHuy.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
    }

    @Override
    public void timKiemHoaDOnTaiQuayDaHuy(Model model, int page, int size, String duLieuTimKiem) {
        HoaDonDTO dto = new HoaDonDTO();
        dto.setLimit(size);
        dto.setDuLieuTimKiem(duLieuTimKiem);
        PageRequest pageRequest = PageRequest.of(page - 1, dto.getLimit(), Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<HoaDon> daHuy = hoaDonRepoditory2.findAllHoaDonDaHuyCoPhanTrang(duLieuTimKiem, pageRequest);
        List<HoaDonDTO> listHoaDonDTO = convertPageToList(daHuy);
        dto.setListHoaDonDTO(listHoaDonDTO);
        model.addAttribute("HoaDonDTO", dto);
        model.addAttribute("duLieuTimKiem", duLieuTimKiem);
        model.addAttribute("pagedaHuy", daHuy.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
    }

    @Override
    public void timKiemTheoNgayHoaDontaiQuayDaHuy(Model model, int page, int size, String duLieuTimKiemString) {
        HoaDonDTO dto = new HoaDonDTO();
        dto.setLimit(size);

        LocalDate duLieuTimKiem = LocalDate.parse(duLieuTimKiemString);
        dto.setDuLieuTimKiem(duLieuTimKiemString);

        PageRequest pageRequest = PageRequest.of(page - 1, dto.getLimit(), Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
        Page<HoaDon> daHuy = hoaDonRepoditory2.findHoaDoDaHuyByNgayTao(duLieuTimKiem, pageRequest);

        List<HoaDonDTO> listHoaDonDTO = convertPageToList(daHuy);
        dto.setListHoaDonDTO(listHoaDonDTO);
        model.addAttribute("HoaDonDTO", dto);
        model.addAttribute("pagedaHuy", daHuy.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("duLieuTimKiem", duLieuTimKiem);
        model.addAttribute("size", size);
    }

    @Override
    public void ChiTietHoaDonTaiQuayDaHuy(Long id, Model model) {
        HoaDon hoaDon = hoaDonRepository.findById(id).get();

        // Lấy danh sách ảnh chính của tất cả sản phẩm và lưu vào List
        List<HoaDonChiTiet> hoaDonChiTiet = hoaDonChiTietRepository2.findHDCT2(id);
        List<String> tenAnhChinhList = new ArrayList<>();
        for (HoaDonChiTiet hoadonCT : hoaDonChiTiet) {
            MauSac mauSac = hoadonCT.getSanPhamChiTiet().getMauSac();
            Long sanPhamId = hoadonCT.getSanPhamChiTiet().getSanPham().getId();

            String tenAnhChinh = hinhAnhRepository.findTenAnhChinhByMauSacIdAndSanPhamId(mauSac.getId(), sanPhamId);
            tenAnhChinhList.add(tenAnhChinh);
        }
        model.addAttribute("tenAnhChinhList", tenAnhChinhList);

        model.addAttribute("hoaDon", hoaDon);
    }
}
