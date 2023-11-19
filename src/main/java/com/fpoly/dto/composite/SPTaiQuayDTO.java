package com.fpoly.dto.composite;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SPTaiQuayDTO {

	private List<ShowSanPhamTaiQuayDTO> lstShowSanPhamTaiQuayDTO;
	
	private Long hoaDonId;
	
	@NotNull(message = "Kích cỡ không được để trống")
	@Min(value = 0, message = "Kích cỡ không được nhỏ hơn 0")
	private Long kichCoId;
	
	@NotNull(message = "Màu sắc không được để trống")
	@Min(value = 0, message = "Màu sắc không được nhỏ hơn 0")
	private Long mauSacId;
	
	private Long sanPhamIdSPTQ;
	
	@NotNull(message = "Số lượng không được để trống")
	@Min(value = 0, message = "Số lượng không được nhỏ hơn 0")
	private Integer SoLuong;
}

