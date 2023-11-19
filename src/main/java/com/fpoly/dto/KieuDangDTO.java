package com.fpoly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KieuDangDTO extends BaseDTO<BaseDTO> {
	
	private String tenKieuDang;
	
	private Boolean daXoa;
}
