package com.fpoly.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fpoly.entity.PhongCach;

public interface PhongCachService {

	List<PhongCach> selectAllPhongCachExist();

	Optional<PhongCach> findById(Long id);

	<S extends PhongCach> S save(S entity);

	Page<PhongCach> selectAllPhongCachExist(Pageable page);

	Page<PhongCach> getPhongCachExistByName(String tenPhongCach, Pageable page);

	void delete(PhongCach entity);

}
