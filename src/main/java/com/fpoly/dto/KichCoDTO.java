package com.fpoly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KichCoDTO extends BaseDTO<BaseDTO> {
	private String tenKichCo;
	
	private Boolean daXoa;
}
