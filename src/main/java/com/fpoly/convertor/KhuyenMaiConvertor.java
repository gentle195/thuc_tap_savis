package com.fpoly.convertor;

import org.springframework.stereotype.Component;

import com.fpoly.dto.KhuyenMaiDTO;
import com.fpoly.entity.KhuyenMai;

@Component
public class KhuyenMaiConvertor {
	public KhuyenMaiDTO toDTO(KhuyenMai entity) {
		KhuyenMaiDTO dto = new KhuyenMaiDTO();
		dto.setId(entity.getId());
		dto.setGiaTriToiThieu(entity.getGiaTriToiThieu());
		dto.setNgayBatDau(entity.getNgayBatDau());
		dto.setNgayKetThuc(entity.getNgayKetThuc());
		dto.setPhanTramGiam(entity.getPhanTramGiam());
		dto.setTenKhuyenMai(entity.getTenKhuyenMai());
		dto.setTrangThai(entity.isTrangThai());
		return dto ;
	}
}
