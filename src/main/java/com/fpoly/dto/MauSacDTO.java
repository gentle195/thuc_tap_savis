package com.fpoly.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MauSacDTO extends BaseDTO<BaseDTO> {
	
	private String tenMauSac;
	
	private String maMauSac;
	
	private Boolean daXoa;
}
