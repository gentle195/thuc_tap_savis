package com.fpoly.convertor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fpoly.config.BcryptedPasswordEncoderConfig;
import com.fpoly.dto.KhachHangDTO;
import com.fpoly.dto.NguoiDungDTO;
import com.fpoly.entity.NguoiDung;

@Component
public class NguoiDungConvertor {
	@Autowired
	private BcryptedPasswordEncoderConfig passwordEncoder ;
	
	
	public NguoiDung toEntityByKhachHangDTO(KhachHangDTO khachHangDTO  ){
		NguoiDung entity = new NguoiDung();
		entity.setEmail(khachHangDTO.getEmail());
		entity.setSoDienThoai(khachHangDTO.getSoDienThoai());
		entity.setTenNguoiDung(khachHangDTO.getHoTen());
		if(khachHangDTO.getMatKhau() != null) {
			entity.setMatKhau(passwordEncoder.encode(khachHangDTO.getMatKhau()));
		}
		if(khachHangDTO.getTrangThai() == 0) {
			entity.setTrangThai(1);
		}
		if(khachHangDTO.getTrangThai() == 1) {
			entity.setTrangThai(0);
		}
		entity.setDaXoa(false);
		return entity ;
	}
	
	
	public NguoiDung toEntityByKhachHangDTOAndOldNguoiDungEntity(KhachHangDTO khachHangDTO , NguoiDung oldEntity ){
		NguoiDung entity = new NguoiDung();
		if(khachHangDTO.getId() != null) {
			entity.setId(oldEntity.getId());
			entity.setDaXoa(oldEntity.getDaXoa());
		}
		entity.setEmail(khachHangDTO.getEmail());
		entity.setSoDienThoai(khachHangDTO.getSoDienThoai());
		entity.setTenNguoiDung(khachHangDTO.getHoTen());
		if(khachHangDTO.getMatKhau() != null) {
			entity.setMatKhau(passwordEncoder.encode(khachHangDTO.getMatKhau()));
		}
		if(khachHangDTO.getTrangThai() == 0) {
			entity.setTrangThai(1);
		}
		if(khachHangDTO.getTrangThai() == 1) {
			entity.setTrangThai(0);
		}
		
		return entity ;
	}
	
	
	public NguoiDungDTO toDTO(NguoiDung entity) {
		NguoiDungDTO dto = new NguoiDungDTO();
		dto.setId(entity.getId());
		dto.setDaXoa(entity.getDaXoa());
		dto.setEmail(entity.getEmail());
		dto.setMatKhau(entity.getMaNguoiDung());
		dto.setSoDienThoai(entity.getSoDienThoai());
		dto.setTenNguoiDung(entity.getTenNguoiDung());
		dto.setTrangThai(entity.getTrangThai());
		dto.setMatKhau(passwordEncoder.encode(entity.getMatKhau()));
		return dto ;
	}
	
}
