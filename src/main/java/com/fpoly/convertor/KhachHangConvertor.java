package com.fpoly.convertor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fpoly.config.BcryptedPasswordEncoderConfig;
import com.fpoly.dto.KhachHangDTO;
import com.fpoly.entity.KhachHang;

@Component
public class KhachHangConvertor {
		
		@Autowired
		private BcryptedPasswordEncoderConfig passwordEncoder ;
		
	
		public KhachHang toEntity(KhachHangDTO dto) {
			KhachHang entity = new KhachHang();
			if(dto.getId() != null) {
				entity.setId(dto.getId());
			}
			entity.setEmail(dto.getEmail());
			entity.setHoTen(dto.getHoTen());
			if(dto.getMatKhau() != null) {
				entity.setMatKhau(passwordEncoder.encode(dto.getMatKhau()));
			}
			entity.setSoDienThoai(dto.getSoDienThoai());
			entity.setSoLanMua(dto.getSoLanMua());
			entity.setTrangThai(dto.getTrangThai());
			entity.setDiaChi(dto.getDiaChiID());
			return entity ;
		}
		
		public KhachHangDTO toDTO(KhachHang entity) {
			KhachHangDTO dto = new KhachHangDTO();
			if(entity.getId() != null) {
				dto.setId(entity.getId());
			}
			dto.setEmail(entity.getEmail());
			dto.setHoTen(entity.getHoTen());
			dto.setSoDienThoai(entity.getSoDienThoai());
			dto.setSoLanMua(entity.getSoLanMua());
			dto.setTrangThai(entity.getTrangThai());
			dto.setNgayTao(entity.getNgayTao());
			dto.setNguoiTao(entity.getNguoiTao());
			dto.setNgayCapNhat(entity.getNgayCapNhat());
			dto.setNguoiCapNhat(entity.getNguoiCapNhat());
			dto.setDiaChiID(entity.getDiaChi());
			return dto ;
		}
	
}
