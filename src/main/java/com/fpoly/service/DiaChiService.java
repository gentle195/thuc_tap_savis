package com.fpoly.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.fpoly.dto.DiaChiDTO;
import com.fpoly.dto.KhachHangDTO;
import org.springframework.stereotype.Service;

@Service
public interface DiaChiService {

	DiaChiDTO save(DiaChiDTO diaChiDTO);

	void delete(long[] ids);

	int countByMaKhachHang(Long id);

	List<DiaChiDTO> findAllDiaChiByMaKhachHang(Long id ,Pageable pageable);

	DiaChiDTO findById(Long id);

	void update(DiaChiDTO diaChiDTO);

	void save(KhachHangDTO khachHangDTO);


}
