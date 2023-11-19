package com.fpoly.dto.search;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SPAndSPCTSearchDto {
	
	private List<Long> kieuDangIds;
	
	private List<Long> chatLieuIds;
	
	private List<Long>  loaiSanPhamIds;
	
	private List<Long>  phongCachIds;
	
	private List<Long> kichCoIds;
	
	private List<Long> mauSacIds;
	
	private String tenSanPham;
	
	@DecimalMin(value = "0", message = "Giá nhỏ nhất không được nhỏ hơn 0")
	private BigDecimal giaMin;
	
	@DecimalMin(value = "0", message = "Giá lớn nhất không được nhỏ hơn 0")
	private BigDecimal giaMax;
	
	@Min(value = 0, message = "Số lượng nhỏ nhất không được nhỏ hơn 0")
	private Integer soLuongMin;
	
	@Min(value = 0, message = "Số lượng lớn nhất không được nhỏ hơn 0")
	private Integer soLuongMax;
	
	private List<Boolean> coHienThi;
	
	private String nguoiTaoSPCT;
	
	private String nguoiCapNhatSPCT;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngayTaoMin;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngayTaoMax;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngayCapNhatMin;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngayCapNhatMax;
	
	// customer search theo tieu chi
	private String tieuChiGia;
	
	// customer search theo gia mac dinh
	private String giaOption;
}
