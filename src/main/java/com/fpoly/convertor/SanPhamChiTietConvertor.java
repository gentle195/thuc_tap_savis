package com.fpoly.convertor;

import org.springframework.stereotype.Component;

import com.fpoly.dto.SanPhamChiTietDTO;
import com.fpoly.entity.SanPhamChiTiet;

@Component
public class SanPhamChiTietConvertor {

	public SanPhamChiTietDTO toDTO(SanPhamChiTiet entity) {
		SanPhamChiTietDTO dto = new SanPhamChiTietDTO();
		dto.setDaXoa(entity.getDaXoa());
		dto.setId(entity.getId());
		dto.setTenKichCo(entity.getKichCo().getTenKichCo());
		dto.setTenMauSac(entity.getMauSac().getTenMauSac());
		dto.setSoLuong(entity.getSoLuong());
		return dto ;
	}
}
