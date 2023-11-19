package com.fpoly.convertor;

import org.springframework.stereotype.Component;

import com.fpoly.dto.NguoiDungDTO;
import com.fpoly.dto.NguoiDungVaiTroDTO;
import com.fpoly.dto.VaiTroDTO;
import com.fpoly.entity.NguoiDungVaiTro;

@Component
public class NguoiDungVaiTroConvertor {
	public NguoiDungVaiTroDTO toDTO(NguoiDungVaiTro entity) {
		NguoiDungVaiTroDTO dto = null;
		NguoiDungDTO nguoiDungDTO = null ;
		VaiTroDTO vaiTroDTO = null ;
		if(entity != null) {
			dto = new NguoiDungVaiTroDTO();
			if(entity.getNguoiDung().getId() != null) {
				nguoiDungDTO = new NguoiDungDTO();
				nguoiDungDTO.setId(entity.getNguoiDung().getId());
				nguoiDungDTO.setDaXoa(entity.getNguoiDung().getDaXoa());
				nguoiDungDTO.setEmail(entity.getNguoiDung().getEmail());
				nguoiDungDTO.setMatKhau(entity.getNguoiDung().getMaNguoiDung());
				nguoiDungDTO.setSoDienThoai(entity.getNguoiDung().getSoDienThoai());
				nguoiDungDTO.setTenNguoiDung(entity.getNguoiDung().getTenNguoiDung());
				nguoiDungDTO.setTrangThai(entity.getNguoiDung().getTrangThai());
				nguoiDungDTO.setMatKhau(entity.getNguoiDung().getMatKhau());
			}
			if(entity.getVaiTro().getId() != null) {
				vaiTroDTO = new VaiTroDTO();
				vaiTroDTO.setId(entity.getNguoiDung().getId());
				vaiTroDTO.setName(entity.getVaiTro().getName());
				vaiTroDTO.setCode(entity.getVaiTro().getCode());
			}
			dto.setId(entity.getId());
			dto.setNguoiDungDTO(nguoiDungDTO);
			dto.setVaiTroDTO(vaiTroDTO);
		}
		return dto ;
	}
}
