package com.fpoly.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fpoly.entity.KichCo;
import com.fpoly.repository.KichCoRepository;
import com.fpoly.service.KichCoService;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Slf4j
public class KichCoServiceImpl implements KichCoService{
	private KichCoRepository kichCoRepository;
	private static Logger logger = LoggerFactory.getLogger(KichCoServiceImpl.class);
	
	@Override
	public List<KichCo> selectAllKichCoExist() {
		return kichCoRepository.selectAllKichCoExist();
	}
	
	@Override
	public <S extends KichCo> S save(S entity) {
		entity.setDaXoa(false);
		return kichCoRepository.save(entity);
	}
	
	@Override
	public Optional<KichCo> findById(Long id) {
		return kichCoRepository.findById(id);
	}
	
	@Override
	public List<KichCo> selectAllKichCoBySanPhamId(Long sanPhamId) {
		return kichCoRepository.selectAllKichCoBySanPhamId(sanPhamId);
	}
	@Override
	public Page<KichCo> selectAllKichCoExist(Pageable pageable) {
		return kichCoRepository.selectAllKichCoExist(pageable);
	}
	@Override
	public Page<KichCo> getKichCoExistByName(String tenKichCo, Pageable pageable) {
		return kichCoRepository.getKichCoExistByName(tenKichCo, pageable);
	}
	@Override
	public void delete(KichCo entity) {
		entity.setDaXoa(true);
		kichCoRepository.save(entity);
	}
	
	
	
}
