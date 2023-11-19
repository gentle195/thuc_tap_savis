package com.fpoly.dto.composite;

import java.util.List;

import com.fpoly.entity.SanPham;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanPhamProductManageDTO {
	private SanPham sanPham;
	
	private int tongSoLuong;
	
	private List<String> anhChinhs;
}
