package com.fpoly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhongCachDTO extends BaseDTO<BaseDTO> {
	
	private int soSanPhamCungPhongCach;
	
	private String tenPhongCach;
	
	private Boolean daXoa;
}
