package com.fpoly.convertor;

import org.springframework.stereotype.Component;

import com.fpoly.dto.HoaDonDTO;
import com.fpoly.entity.HoaDon;

@Component
public class HoaDonConvertor {
	public HoaDonDTO toDTO(HoaDon entity) {
		HoaDonDTO dto = new HoaDonDTO();
		dto.setId(entity.getId());
		dto.setMaDonHang(entity.getMaDon());
		dto.setNguoiNhan(entity.getNguoiNhan());
		dto.setSdtNguoiNhan(entity.getSdtNguoiNhan());
		if(entity.getKhuyenMai() != null) {
			dto.setTenKhuyenMai(entity.getKhuyenMai().getTenKhuyenMai());
		}
		dto.setTienShip(entity.getTienShip());
		dto.setTongTienDonHang(entity.getTongTienDonHang());
		dto.setTongTienHoaDon(entity.getTongTienHoaDon());
		dto.setGhiChu(entity.getGhiChu());
		dto.setTrangThai(entity.getTrangThai().getName());
		
		return dto ;
	}
}
