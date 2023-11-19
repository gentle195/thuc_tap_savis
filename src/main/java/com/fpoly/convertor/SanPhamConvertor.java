package com.fpoly.convertor;

import org.springframework.stereotype.Component;

import com.fpoly.dto.SanPhamDTO;
import com.fpoly.entity.SanPham;

@Component
public class SanPhamConvertor {
	
	public SanPhamDTO toDTO(SanPham entity) {
		SanPhamDTO dto = new SanPhamDTO();
		dto.setId(entity.getId());
		dto.setGia(entity.getGia());
		dto.setMoTa(entity.getMoTa());
		dto.setTenSanPham(entity.getTenSanPham());
		dto.setTenChatLieu(entity.getChatLieu().getTenChatLieu());
		dto.setTenKieuDang(entity.getKieuDang().getTenKieuDang());
		dto.setTenLoaiSanPham(entity.getLoaiSanPham().getTenLoaiSanPham());
		dto.setTenPhongCach(entity.getPhongCach().getTenPhongCach());
		return dto ;
	}
	
}
