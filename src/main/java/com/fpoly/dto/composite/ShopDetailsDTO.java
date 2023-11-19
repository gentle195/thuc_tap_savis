package com.fpoly.dto.composite;

import java.math.BigDecimal;
import java.util.List;

import com.fpoly.entity.KichCo;
import com.fpoly.entity.MauSac;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopDetailsDTO {
	private Long sanPhamId;
	
	private String tenSanPham;
	
	private BigDecimal gia;
	
	private List<String> anhChinhs;
	
	private List<String> anhChinhs1;
	
	private List<String> anhChinhs2;
	
	private List<KichCo> lstKichCo;
	
	private List<MauSac> lstMauSac;
	
	private int soLuongConLai;

	private Long hoaDonId;
	
	private Long kichCoId;
	
	private Long mauSacId;
	
	private String moTa;
	
	private Integer soLuong;
}
