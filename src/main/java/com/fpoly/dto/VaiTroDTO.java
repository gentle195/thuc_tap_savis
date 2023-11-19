package com.fpoly.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaiTroDTO extends BaseDTO<VaiTroDTO> {
	
	private String name ;
	
	private String code ;
	
	private List<NguoiDungVaiTroDTO> listNguoiDungVaiTroDTO = new ArrayList<NguoiDungVaiTroDTO>() ;
}
