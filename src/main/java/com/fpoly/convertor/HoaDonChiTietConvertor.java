package com.fpoly.convertor;

import org.springframework.stereotype.Component;

import com.fpoly.dto.HoaDonChiTietDTO;
import com.fpoly.entity.HoaDonChiTiet;

@Component
public class HoaDonChiTietConvertor {
	public HoaDonChiTietDTO toDTO(HoaDonChiTiet entity) {
		HoaDonChiTietDTO dto = new HoaDonChiTietDTO();
		dto.setId(entity.getId());
		dto.setDonGia(entity.getDonGia());
		dto.setSoLuong(entity.getSoLuong());
		dto.setTongTien(entity.getTongTien());
		
		return dto ;
	}
}
