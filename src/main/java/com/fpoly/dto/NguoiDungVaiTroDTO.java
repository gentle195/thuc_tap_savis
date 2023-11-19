package com.fpoly.dto;

import java.util.List;

import com.fpoly.entity.NguoiDungVaiTro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NguoiDungVaiTroDTO {
	private Long id;
	
	private NguoiDungDTO nguoiDungDTO ;
	
	private VaiTroDTO vaiTroDTO ;
	
}
