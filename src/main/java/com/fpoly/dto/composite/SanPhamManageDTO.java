package com.fpoly.dto.composite;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.fpoly.dto.SanPhamChiTietDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamManageDTO {
	private Long sanPhamId;
	
	@Min(value = 0, message = "Kiểu dáng sản phẩm không được để trống")
	private Long kieuDangId;
	
	@Min(value = 0, message = "Chất liệu sản phẩm không được để trống")
	private Long chatLieuId;
	
	@Min(value = 0, message = "Loại sản phẩm không được để trống")
	private Long loaiSanPhamId;
	
	@Min(value = 0, message = "Phong cách sản phẩm không được để trống")
	private Long phongCachId;
	
	private Boolean daXoa;
	
	@NotEmpty(message = "Tên sản phẩm không được để trống")
	private String tenSanPham;
	
	@NotEmpty(message = "Mô tả sản phẩm không được để trống")
	private String moTa;
	
	@NotNull(message = "Số lượng không được để trống")
	@Min(value = 0, message = "Số lượng không được nhỏ hơn 0")
	private Integer soLuong;
	
	@DecimalMin(value = "1000", message = "Giá không được nhỏ hơn 1.000")
	@NotNull(message = "Giá không được để trống")
	private BigDecimal gia;
	
	@NotEmpty(message = "Danh sách kích cỡ sản phẩm không được để trống")
	private List<@Valid Long> kichCoIds;
	
	@NotEmpty(message = "Danh sách màu sắc sản phẩm không được để trống")
	private List<@Valid Long> mauSacIds;
	
	private HashMap<Long, String> lstMauSacAddImg;
	 
	private Boolean isEdit = false;
	
	private Boolean isCreatedImg = false;
	
	private Boolean isCreatedValueImg = false;
	
	private Boolean isGenaratedData = false;
	
	private Boolean isAddProductImageSuccess = false;
	
	private Boolean isAddProductSuccess = false;
	
	private Boolean isHaveImg = false;
	
	private List<HinhAnhMauSacDTO> lstHinhAnhMauSacDTO;
}
