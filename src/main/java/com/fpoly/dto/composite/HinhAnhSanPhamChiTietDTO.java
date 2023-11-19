package com.fpoly.dto.composite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HinhAnhSanPhamChiTietDTO {
	private Long sanPhamChiTietId;
	
	private String anhChinh;
	
	private Long mauSacId;
}
