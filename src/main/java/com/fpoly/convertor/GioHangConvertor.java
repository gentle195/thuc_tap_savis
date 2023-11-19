package com.fpoly.convertor;

import org.springframework.stereotype.Component;

import com.fpoly.dto.GioHangDTO;
import com.fpoly.entity.GioHang;

@Component
public class GioHangConvertor {
	
	public GioHangDTO toDTO(GioHang entity) {
		GioHangDTO dto = new GioHangDTO();
		dto.setId(entity.getId());
		dto.setTongTien(entity.getTongTien());
		dto.setTrangThai(entity.getTrangThai());
		dto.setKhachHangId(entity.getId());
		return dto ;
	}
	
	public GioHang toEntity(GioHangDTO dto) {
		GioHang entity = new GioHang();
		if(dto.getId() != null) {
			entity.setId(entity.getId());
		}
		entity.setTongTien(entity.getTongTien());
		entity.setTrangThai(entity.getTrangThai());
		return entity ;
	}
	
}
