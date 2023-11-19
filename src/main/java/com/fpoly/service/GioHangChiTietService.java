package com.fpoly.service;

import java.util.List;

import com.fpoly.dto.GioHangChiTietDTO;
import com.fpoly.entity.GioHangChiTiet;

public interface GioHangChiTietService  {

	List<GioHangChiTietDTO> findAllByGioHangId(Long id);

	void capNhatSoLuongGioHangChiTiet(Long[] ids, Integer[] soLuongs,Integer[] donGias);

	void capNhatGioHangThanhDaXoaById(Long id);

	void capNhatTatCaGioHangThanhDaXoaById();

	Integer getTongTienByKhachHangID(Long id);

	GioHangChiTietDTO findById(Long id);

}
