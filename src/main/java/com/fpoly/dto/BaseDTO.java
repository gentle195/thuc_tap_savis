package com.fpoly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseDTO<T extends BaseDTO> {

	private Long id;
	
	private Date ngayTao;
	
	private String nguoiTao;
	
	private Date ngayCapNhat;
	
	private String nguoiCapNhat;
}
