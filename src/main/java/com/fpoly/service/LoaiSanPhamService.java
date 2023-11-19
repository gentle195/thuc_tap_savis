package com.fpoly.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fpoly.entity.LoaiSanPham;


public interface LoaiSanPhamService {

	List<LoaiSanPham> selectAllLoaiHangExist();

	Optional<LoaiSanPham> findById(Long id);

	<S extends LoaiSanPham> S save(S entity);

	Page<LoaiSanPham> selectAllLoaiSanPhamExist(Pageable page);

	Page<LoaiSanPham> getLoaiSanPhamExistByName(String tenLoaiSanPham, Pageable page);

	void delete(LoaiSanPham entity);
}
