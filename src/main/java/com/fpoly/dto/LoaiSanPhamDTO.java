package com.fpoly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoaiSanPhamDTO extends BaseDTO<BaseDTO> {
	
	private int soSanPhamCungLoai;
	
	private String tenLoaiSanPham;
	
	private Boolean daXoa;
}
