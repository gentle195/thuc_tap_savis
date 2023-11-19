package com.fpoly.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatLieuDTO extends BaseDTO<BaseDTO> {
	private int soSanPhamCungChatLieu;
	
	private String tenChatLieu;
	
	private String tenChatLieuSearch;
	
	private Boolean daXoa;
}
