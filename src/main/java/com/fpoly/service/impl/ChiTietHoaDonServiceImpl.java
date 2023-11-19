package com.fpoly.service.impl;

import com.fpoly.entity.*;
import com.fpoly.repository.*;
import com.fpoly.service.ChiTietHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChiTietHoaDonServiceImpl implements ChiTietHoaDonService {
    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    HoaDonChiTietRepository2 hoaDonChiTietRepository2;

    @Autowired
    HinhAnhRepository hinhAnhRepository;

    @Autowired
    LichSuHoaDonRepository lichSuHoaDonRepository;

    @Autowired
    GiaoDichRepository giaoDichRepository;

    @Override
    public void choXacNhan(Long id, Model model) {
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

        List<lichSuHoaDon> lichSuHD = lichSuHoaDonRepository.findLichSuByHDID(id);
        model.addAttribute("lichSuHD", lichSuHD);

        List<GiaoDich> timeLineChoXacNhan = giaoDichRepository.findByTrangThaiIdAndHoaDonId(1, id);
        model.addAttribute("timeLineChoXacNhan", timeLineChoXacNhan);

        model.addAttribute("hoaDon", hoaDon);
    }

    @Override
    public void choGiaoHang(Long id, Model model) {
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        List<GiaoDich> timeLineChoXacNhan = giaoDichRepository.findByTrangThaiIdAndHoaDonId(1, id);
        List<GiaoDich> timeLineChoGiaoHang = giaoDichRepository.findByTrangThaiIdAndHoaDonId(2, id);
        // Lấy danh sách ảnh chính của tất cả sản phẩm và lưu vào List
        List<HoaDonChiTiet> hoaDonChiTiet = hoaDonChiTietRepository2.findHDCT(id);
        List<String> tenAnhChinhList = new ArrayList<>();
        for (HoaDonChiTiet hoadonCT : hoaDonChiTiet) {
            MauSac mauSac = hoadonCT.getSanPhamChiTiet().getMauSac();
            Long sanPhamId = hoadonCT.getSanPhamChiTiet().getSanPham().getId();

            String tenAnhChinh = hinhAnhRepository.findTenAnhChinhByMauSacIdAndSanPhamId(mauSac.getId(), sanPhamId);
            tenAnhChinhList.add(tenAnhChinh);
        }

        List<lichSuHoaDon> lichSuHD = lichSuHoaDonRepository.findLichSuByHDID(id);
        model.addAttribute("lichSuHD", lichSuHD);

        model.addAttribute("tenAnhChinhList", tenAnhChinhList);
        model.addAttribute("timeLineChoXacNhan", timeLineChoXacNhan);
        model.addAttribute("timeLineChoGiaoHang", timeLineChoGiaoHang);
        model.addAttribute("hoaDon", hoaDon);
    }

    @Override
    public void dangGiaoHang(Long id, Model model) {
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        List<GiaoDich> timeLineChoXacNhan = giaoDichRepository.findByTrangThaiIdAndHoaDonId(1, id);
        List<GiaoDich> timeLineChoGiaoHang = giaoDichRepository.findByTrangThaiIdAndHoaDonId(2, id);
        List<GiaoDich> timeLineDangGiaoHang = giaoDichRepository.findByTrangThaiIdAndHoaDonId(3, id);
        // Lấy danh sách ảnh chính của tất cả sản phẩm và lưu vào List
        List<HoaDonChiTiet> hoaDonChiTiet = hoaDonChiTietRepository2.findHDCT(id);
        List<String> tenAnhChinhList = new ArrayList<>();
        for (HoaDonChiTiet hoadonCT : hoaDonChiTiet) {
            MauSac mauSac = hoadonCT.getSanPhamChiTiet().getMauSac();
            Long sanPhamId = hoadonCT.getSanPhamChiTiet().getSanPham().getId();

            String tenAnhChinh = hinhAnhRepository.findTenAnhChinhByMauSacIdAndSanPhamId(mauSac.getId(), sanPhamId);
            tenAnhChinhList.add(tenAnhChinh);
        }

        List<lichSuHoaDon> lichSuHD = lichSuHoaDonRepository.findLichSuByHDID(id);
        model.addAttribute("lichSuHD", lichSuHD);

        model.addAttribute("tenAnhChinhList", tenAnhChinhList);
        model.addAttribute("timeLineChoXacNhan", timeLineChoXacNhan);
        model.addAttribute("timeLineChoGiaoHang", timeLineChoGiaoHang);
        model.addAttribute("timeLineDangGiaoHang", timeLineDangGiaoHang);
        model.addAttribute("hoaDon", hoaDon);
    }

    @Override
    public void daGiaoHang(Long id, Model model) {
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        List<GiaoDich> timeLineChoXacNhan = giaoDichRepository.findByTrangThaiIdAndHoaDonId(1, id);
        List<GiaoDich> timeLineChoGiaoHang = giaoDichRepository.findByTrangThaiIdAndHoaDonId(2, id);
        List<GiaoDich> timeLineDangGiaoHang = giaoDichRepository.findByTrangThaiIdAndHoaDonId(3, id);
        List<GiaoDich> timeLineDaGiaoHang = giaoDichRepository.findByTrangThaiIdAndHoaDonId(4, id);
        // Lấy danh sách ảnh chính của tất cả sản phẩm và lưu vào List
        List<HoaDonChiTiet> hoaDonChiTiet = hoaDonChiTietRepository2.findHDCT(id);
        List<String> tenAnhChinhList = new ArrayList<>();
        for (HoaDonChiTiet hoadonCT : hoaDonChiTiet) {
            MauSac mauSac = hoadonCT.getSanPhamChiTiet().getMauSac();
            Long sanPhamId = hoadonCT.getSanPhamChiTiet().getSanPham().getId();

            String tenAnhChinh = hinhAnhRepository.findTenAnhChinhByMauSacIdAndSanPhamId(mauSac.getId(), sanPhamId);
            tenAnhChinhList.add(tenAnhChinh);
        }

        List<lichSuHoaDon> lichSuHD = lichSuHoaDonRepository.findLichSuByHDID(id);
        model.addAttribute("lichSuHD", lichSuHD);

        model.addAttribute("tenAnhChinhList", tenAnhChinhList);
        model.addAttribute("timeLineChoXacNhan", timeLineChoXacNhan);
        model.addAttribute("timeLineChoGiaoHang", timeLineChoGiaoHang);
        model.addAttribute("timeLineDangGiaoHang", timeLineDangGiaoHang);
        model.addAttribute("timeLineDaGiaoHang", timeLineDaGiaoHang);
        model.addAttribute("hoaDon", hoaDon);
    }

    @Override
    public void daHuy(Long id, Model model) {
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        List<GiaoDich> timeLineChoXacNhan = giaoDichRepository.findByTrangThaiIdAndHoaDonId(1, id);
        List<GiaoDich> timeLineHuyDonHang = giaoDichRepository.findByTrangThaiIdAndHoaDonId(5, id);
        // Lấy danh sách ảnh chính của tất cả sản phẩm và lưu vào List
        List<HoaDonChiTiet> hoaDonChiTiet = hoaDonChiTietRepository2.findHDCT(id);
        List<String> tenAnhChinhList = new ArrayList<>();
        for (HoaDonChiTiet hoadonCT : hoaDonChiTiet) {
            MauSac mauSac = hoadonCT.getSanPhamChiTiet().getMauSac();
            Long sanPhamId = hoadonCT.getSanPhamChiTiet().getSanPham().getId();

            String tenAnhChinh = hinhAnhRepository.findTenAnhChinhByMauSacIdAndSanPhamId(mauSac.getId(), sanPhamId);
            tenAnhChinhList.add(tenAnhChinh);
        }

        List<lichSuHoaDon> lichSuHD = lichSuHoaDonRepository.findLichSuByHDID(id);
        model.addAttribute("lichSuHD", lichSuHD);

        model.addAttribute("tenAnhChinhList", tenAnhChinhList);
        model.addAttribute("timeLineChoXacNhan", timeLineChoXacNhan);
        model.addAttribute("timeLineHuyDonHang", timeLineHuyDonHang);
        model.addAttribute("hoaDon", hoaDon);
    }
}
