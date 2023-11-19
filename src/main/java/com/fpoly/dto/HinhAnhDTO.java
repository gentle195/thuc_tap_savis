package com.fpoly.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HinhAnhDTO {
	
	private Long hinhAnhid;
	
	private String tenAnh;
	
	private Boolean laAnhChinh;

	private Boolean coHienThi;
}
