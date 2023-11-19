package com.fpoly.convertor;

import org.springframework.stereotype.Component;

import com.fpoly.dto.GioHangChiTietDTO;
import com.fpoly.entity.GioHangChiTiet;

@Component
public class GioHangChiTietConvertor {
	public GioHangChiTietDTO toDTO(GioHangChiTiet entity) {
		GioHangChiTietDTO dto = new GioHangChiTietDTO();
		dto.setId(entity.getId());
		dto.setDaXoa(entity.getDaXoa());
		dto.setDonGia(entity.getDonGia());
		dto.setSoLuong(entity.getSoLuong());
		dto.setThanhTien(entity.getThanhTien());
		dto.setTrangThai(entity.getTrangThai());
		dto.setGioHangId(entity.getGioHang().getId());
		dto.setSanPhamChiTietId(entity.getSanPhamChiTiet().getId());
		return dto ;
	}
	
	public GioHangChiTiet toEntity(GioHangChiTietDTO dto) {
		GioHangChiTiet entity = new GioHangChiTiet();
		if(dto.getId() != null) {
			entity.setId(dto.getId());
		}
		entity.setDaXoa(dto.getDaXoa());
		entity.setDonGia(dto.getDonGia());
		entity.setSoLuong(dto.getSoLuong());
		entity.setThanhTien(dto.getThanhTien());
		entity.setTrangThai(dto.getTrangThai());
		return entity ;
	}
}
