package com.fpoly.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fpoly.entity.KieuDang;


public interface KieuDangService {

	List<KieuDang> selectAllKieuDangExist();

	Optional<KieuDang> findById(Long id);

	<S extends KieuDang> S save(S entity);

	Page<KieuDang> selectAllKieuDangExist(Pageable page);

	void delete(KieuDang entity);

	Page<KieuDang> getKieuDangExistByName(String tenKieuDang, Pageable page);


}
