package com.fpoly.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fpoly.dto.search.SPAndSPCTSearchDto;
import com.fpoly.entity.SanPham;


public interface SanPhamService {
	Optional<SanPham> findById(Long id);

	<S extends SanPham> S save(S entity);

	Page<SanPham> getSanPhamExist(Pageable pageable);

	Page<SanPham> searchProductExist(SPAndSPCTSearchDto data, Pageable pageable);

	void delete(SanPham entity);

	Page<SanPham> showSanPhamExistHomePage(Pageable pageable);

	int selectCountSanPhamByLoaiSanPhamId(Long loaiSanPhamId);

	int selectCountSanPhamByPhongCachId(Long phongCachId);

	int selectCountSanPhamByChatLieuId(Long chatLieuId);

}
