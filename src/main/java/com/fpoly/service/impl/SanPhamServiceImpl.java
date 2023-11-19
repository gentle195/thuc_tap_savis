package com.fpoly.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.fpoly.dto.search.SPAndSPCTSearchDto;
import com.fpoly.entity.SanPham;
import com.fpoly.repository.SanPhamRepository;
import com.fpoly.repository.SanPhamSearchRepository;
import com.fpoly.service.SanPhamService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SanPhamServiceImpl implements SanPhamService{
	private final SanPhamRepository sanPhamRepository;
	private final SanPhamSearchRepository sanPhamSearchRepository;
	
	@Override
	public <S extends SanPham> S save(S entity) {
		entity.setDaXoa(false);
		return sanPhamRepository.save(entity);
	}

	@Override
	public Optional<SanPham> findById(Long id) {
		return sanPhamRepository.findById(id);
	}

	@Override
	public Page<SanPham> getSanPhamExist(Pageable pageable) {
		return sanPhamRepository.getSanPhamExist(pageable);
	}
	
	@Override
	public Page<SanPham> searchProductExist(SPAndSPCTSearchDto data, Pageable pageable) {
		return sanPhamSearchRepository.searchProductExist(data, pageable);
	}
	
	@Override
	public void delete(SanPham entity) {
		entity.setDaXoa(true);
		sanPhamRepository.save(entity);
	}
	
	@Override
	public Page<SanPham> showSanPhamExistHomePage(Pageable pageable) {
		return sanPhamRepository.showSanPhamExistHomePage(pageable);
	}
	
	@Override
	public int selectCountSanPhamByLoaiSanPhamId(Long loaiSanPhamId) {
		return sanPhamRepository.selectCountSanPhamByLoaiSanPhamId(loaiSanPhamId);
	}
	
	@Override
	public int selectCountSanPhamByPhongCachId(Long phongCachId) {
		return sanPhamRepository.selectCountSanPhamByPhongCachId(phongCachId);
	}
	
	@Override
	public int selectCountSanPhamByChatLieuId(Long chatLieuId) {
		return sanPhamRepository.selectCountSanPhamByChatLieuId(chatLieuId);
	}	
	
	
}
