package com.fpoly.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fpoly.entity.KichCo;


public interface KichCoService {

	List<KichCo> selectAllKichCoExist();

	Optional<KichCo> findById(Long id);

	<S extends KichCo> S save(S entity);

	List<KichCo> selectAllKichCoBySanPhamId(Long sanPhamId);

	Page<KichCo> selectAllKichCoExist(Pageable pageable);

	Page<KichCo> getKichCoExistByName(String tenKichCo, Pageable pageable);

	void delete(KichCo entity);

}
