package com.fpoly.dto;

import java.util.ArrayList;
import java.util.List;

import com.fpoly.entity.NguoiDungVaiTro;

//import com.fpoly.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NguoiDungDTO extends BaseDTO<NguoiDungDTO> {
	
	private String tenDangNhap;

	private String maNhanVien;

	private String matKhau;
	
	private String email;
	
	private String tenNguoiDung;

	private String ho;

	private String ten;

	private String soDienThoai;

	private String duLieuTimKiem;
	
//	private Role role;

	private int trangThai;

	private Boolean daXoa;

	private String anhNhanVien;
	
	private List<NguoiDungVaiTroDTO> listNguoiDungVaiTroDTO = new ArrayList<NguoiDungVaiTroDTO>() ;

	private List<NguoiDungDTO> listNguoiDungDTO = new ArrayList<NguoiDungDTO>() ;
}
