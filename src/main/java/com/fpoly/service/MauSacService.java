package com.fpoly.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fpoly.entity.MauSac;



public interface MauSacService {

	List<MauSac> selectAllMauSacExist();

	Optional<MauSac> findById(Long id);

	<S extends MauSac> S save(S entity);

	List<MauSac> getAllMauSacExistBySPId(Long spId);

	Page<MauSac> selectAllMauSacExist(Pageable page);

	Page<MauSac> getMauSacExistByName(String tenMauSac, Pageable page);

	void delete(MauSac entity);

}
