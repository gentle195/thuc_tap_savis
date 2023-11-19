package com.fpoly.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fpoly.dto.search.SPAndSPCTSearchDto;
import com.fpoly.entity.SanPham;

public interface SanPhamSearchRepository {

	Page<SanPham> searchProductExist(SPAndSPCTSearchDto data, Pageable pageable);
	
}
