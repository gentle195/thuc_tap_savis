package com.fpoly.service.impl;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fpoly.entity.*;
import com.fpoly.repository.*;
import com.fpoly.service.SanPhamChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fpoly.convertor.GioHangChiTietConvertor;
import com.fpoly.convertor.GioHangConvertor;
import com.fpoly.convertor.SanPhamChiTietConvertor;
import com.fpoly.convertor.SanPhamConvertor;
import com.fpoly.dto.GioHangChiTietDTO;
import com.fpoly.dto.GioHangDTO;
import com.fpoly.dto.SanPhamChiTietDTO;
import com.fpoly.dto.SanPhamDTO;
import com.fpoly.service.GioHangService;

@Service
public class GioHangServiceImpl implements GioHangService {

    @Autowired
    private GioHangRepository gioHangRepo;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepo;

    @Autowired
    private GioHangConvertor gioHangConvertor;

    @Autowired
    private GioHangChiTietConvertor gioHangChiTietConvertor;

    @Autowired
    private SanPhamChiTietConvertor sanPhamChiTietConvertor;

    @Autowired
    private SanPhamConvertor sanPhamConvertor;

    @Autowired
    private HinhAnhRepository hinhAnhRepository;

    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    GioHangRepository gioHangRepository;

    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    SanPhamChiTietService sanPhamChiTietService;

    @Autowired
    GioHangChiTietRepository gioHangChiTietRepository;

    @Override
    public GioHangDTO findByKhachHangId(Long id) {
        GioHangDTO gioHangDTO = null;
        if (id != null) {
            gioHangDTO = new GioHangDTO();
            List<GioHangChiTietDTO> listGioHangChiTietDTO = new ArrayList<GioHangChiTietDTO>();
            GioHang gioHangEntity = gioHangRepo.findGioHangByKhachHangId(id);
            if (gioHangEntity != null) {
                for (GioHangChiTiet gioHangChiTiet : gioHangEntity.getGioHangChiTiets()) {
                    if (gioHangChiTiet.getDaXoa() == false) {
                        GioHangChiTietDTO gioHangChiTietDTO = gioHangChiTietConvertor.toDTO(gioHangChiTiet);
                        SanPhamChiTietDTO sanPhamChiTietDTO = sanPhamChiTietConvertor.toDTO(gioHangChiTiet.getSanPhamChiTiet());
                        sanPhamChiTietDTO.setSanPhamDTO(sanPhamConvertor.toDTO(gioHangChiTiet.getSanPhamChiTiet().getSanPham()));
                        HinhAnh hinhAnh = hinhAnhRepository
                                .findByHinhAnhByMauSacIdVaLaAnhChinh(gioHangChiTiet.getSanPhamChiTiet().getMauSac().getId()
                                        , gioHangChiTiet.getSanPhamChiTiet().getSanPham().getId()
                                );
                        sanPhamChiTietDTO.getSanPhamDTO()
                                .setTenHinhAnh(hinhAnh.getTenAnh());
                        gioHangChiTietDTO.setSanPhamChiTietDTO(sanPhamChiTietDTO);
                        listGioHangChiTietDTO.add(gioHangChiTietDTO);
                    }
                }
                gioHangDTO = gioHangConvertor.toDTO(gioHangEntity);
                gioHangDTO.setListGioHangChiTiets(listGioHangChiTietDTO);

                return gioHangDTO;
            }
        }
        return null;
    }

    @Override
    public void capNhatTongTien(Long id) {
        GioHang gioHang = gioHangRepo.findGioHangByKhachHangId(id);
        if (gioHang != null) {
            gioHang.setTongTien(gioHangChiTietRepo.tongTien(gioHang.getId()));
            gioHangRepo.save(gioHang);
        }
        return;
    }

    @Override
    public int tinhTienGioHangTheoMaGioHangChiTiet(long[] idGioHangChiTiet) {
        int thanhTien = 0;
        for (int i = 0; i < idGioHangChiTiet.length; i++) {
            Long id = (Long) Array.get(idGioHangChiTiet, i);
            GioHangChiTiet gioHangChiTiet = gioHangChiTietRepo.findById(id).get();
            thanhTien += Integer.parseInt(gioHangChiTiet.getThanhTien().toString());
        }
        return thanhTien;
    }

    @Override
    public ResponseEntity<String> addToCart(Long sanPhamId, Long mauSacId, Long kichCoId, Integer soLuong) {
        String auth = SecurityContextHolder.getContext().getAuthentication().getName();
        String message = "";
        if (auth == null) {
            message = "Lỗi";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
        KhachHang khachHang = khachHangRepository.findByEmail(auth);
        GioHang gioHang = gioHangRepository.findGioHangsByKhachHangId(khachHang.getId());

        Optional<SanPhamChiTiet> opt = sanPhamChiTietService.getSanPhamChiTietByMauSacSizeSanPhamId(sanPhamId, mauSacId, kichCoId);
        Integer soLuongBanDau = sanPhamChiTietRepository.laySoLuongSanPhamChiTiet2(kichCoId, mauSacId, sanPhamId);

        if (opt.isPresent() && gioHang != null) {
            SanPhamChiTiet sanPhamChiTiet = opt.get();
            BigDecimal donGia = sanPhamChiTiet.getSanPham().getGia();
            BigDecimal thanhTien = donGia.multiply(BigDecimal.valueOf(soLuong));
            Integer thanhTienInt = thanhTien.intValue();

            Optional<GioHangChiTiet> existingCartItem = gioHangChiTietRepository.findBySanPhamChiTietAndGioHang(sanPhamChiTiet.getId(), gioHang.getId());
            GioHangChiTiet gioHangChiTiet;
            if (existingCartItem.isPresent()) {
                gioHangChiTiet = existingCartItem.get();
                Integer soLuongCu = gioHangChiTiet.getSoLuong();
                gioHangChiTiet.setSoLuong(soLuongCu + soLuong);
                gioHangChiTiet.setThanhTien(gioHangChiTiet.getThanhTien().add(thanhTien));
            } else {
                gioHangChiTiet = new GioHangChiTiet();
                gioHangChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                gioHangChiTiet.setSoLuong(soLuong);
                gioHangChiTiet.setGioHang(gioHang);
                gioHangChiTiet.setDonGia(donGia.intValue());
                gioHangChiTiet.setThanhTien(thanhTien);
                gioHangChiTiet.setDaXoa(false);
            }
            gioHangChiTietRepository.save(gioHangChiTiet);

            gioHang.setTongTien(gioHang.getTongTien() + thanhTienInt);
            gioHangRepository.save(gioHang);

            Integer soLuongThemVao = soLuong;
            Integer soLuongCapNhat = soLuongBanDau - soLuongThemVao;
            sanPhamChiTiet.setSoLuong(soLuongCapNhat);
            sanPhamChiTietRepository.save(sanPhamChiTiet);

            message = "Thêm mới thành công";
            return ResponseEntity.ok(message);
        } else {
            String errorMessage = "Lỗi khi thêm vào giỏ hàng";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @Override
    public void xoaSachGioHang(long id) {
        Optional<GioHang> optGioHang = gioHangRepository.findById(id);
        if (optGioHang.isPresent()) {
            GioHang gioHang = optGioHang.get();
            List<GioHangChiTiet> gioHangChiTiets = gioHangChiTietRepository.findbyGiohangIdAndDaXoa(id);

            for (GioHangChiTiet gioHangChiTiet : gioHangChiTiets) {
                gioHangChiTiet.setDaXoa(true);
                gioHangChiTietRepository.save(gioHangChiTiet);

                SanPhamChiTiet sanPhamChiTiet = gioHangChiTiet.getSanPhamChiTiet();
                Integer soLuongSPCTBanDau = sanPhamChiTiet.getSoLuong();
                Integer soLuongNhapVao = gioHangChiTiet.getSoLuong();
                Integer soLuongcapNhat = soLuongSPCTBanDau + soLuongNhapVao;
                sanPhamChiTiet.setSoLuong(soLuongcapNhat);
                sanPhamChiTietRepository.save(sanPhamChiTiet);
            }
        }
    }
}
