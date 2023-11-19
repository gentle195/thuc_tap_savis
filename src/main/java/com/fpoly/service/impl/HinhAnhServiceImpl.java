package com.fpoly.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fpoly.entity.HinhAnh;
import com.fpoly.repository.HinhAnhRepository;
import com.fpoly.service.HinhAnhService;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class HinhAnhServiceImpl implements HinhAnhService{
	private HinhAnhRepository hinhAnhRepository;

	@Override
	public <S extends HinhAnh> S save(S entity) {
		return hinhAnhRepository.save(entity);
	}

	@Override
	public Optional<HinhAnh> findById(Long id) {
		return hinhAnhRepository.findById(id);
	}

	@Override
	public void delete(HinhAnh entity) {
		hinhAnhRepository.delete(entity);
	}
	@Override
	public List<HinhAnh> getLstHinhAnhMauSacBySanPhamId(Long sanPhamId) {
		return hinhAnhRepository.getLstHinhAnhMauSacBySanPhamId(sanPhamId);
	}
	
	@Override
	public List<Long> getDistinctMauSacInHinhAnhBySanPhamId(Long sanPhamId) {
		return hinhAnhRepository.getDistinctMauSacInHinhAnhBySanPhamId(sanPhamId);
	}
	
	@Override
	public List<HinhAnh> getHinhAnhChinhExist() {
		return hinhAnhRepository.getHinhAnhChinhExist();
	}
	
	@Override
	public Optional<HinhAnh> getHinhAnhByName(String tenAnh) {
		return hinhAnhRepository.getHinhAnhByName(tenAnh);
	}
	
	@Override
	public Optional<HinhAnh> getHinhAnhChinhBySanPhamIdAndMauSacId(Long sanPhamId, Long mauSacId) {
		return hinhAnhRepository.getHinhAnhChinhBySanPhamIdAndMauSacId(sanPhamId, mauSacId);
	}
	
	@Override
	public List<HinhAnh> getHinhAnhChinhBySanPhamIdAndMauSacIds(Long sanPhamId, List<Long> mauSacIds) {
		return hinhAnhRepository.getHinhAnhChinhBySanPhamIdAndMauSacIds(sanPhamId, mauSacIds);
	}
	
	@Override
	public int getCountHinhAnhChinhBySanPhamIdAndMauSacId(Long sanPhamId, Long mauSacId) {
		return hinhAnhRepository.getCountHinhAnhChinhBySanPhamIdAndMauSacId(sanPhamId, mauSacId);
	}
	
	@Override
	public List<HinhAnh> getHinhAnhChinhBySanPhamId(Long sanPhamId) {
		return hinhAnhRepository.getHinhAnhChinhBySanPhamId(sanPhamId);
	}
	
	@Override
	public List<HinhAnh> getHinhAnhBySanPhamIdAndMauSacIds(Long sanPhamId, List<Long> mauSacIds) {
		return hinhAnhRepository.getHinhAnhBySanPhamIdAndMauSacIds(sanPhamId, mauSacIds);
	}
	
	@Override
	public List<HinhAnh> getHinhAnhBySanPhamId(Long sanPhamId) {
		return hinhAnhRepository.getHinhAnhBySanPhamId(sanPhamId);
	}
	@Override
	public List<HinhAnh> getHinhAnhBySanPhamIdAndMauSacId(Long sanPhamId, Long mauSacId) {
		return hinhAnhRepository.getHinhAnhBySanPhamIdAndMauSacId(sanPhamId, mauSacId);
	}
	
	@Override
	public int getCountHinhAnhBySanPhamId(Long sanPhamId) {
		return hinhAnhRepository.getCountHinhAnhBySanPhamId(sanPhamId);
	}
	
	@Override
	public int getCountHinhAnhChoPhepThemBySanPhamId(Long sanPhamId) {
		return hinhAnhRepository.getCountHinhAnhChoPhepThemBySanPhamId(sanPhamId);
	}	
	
}
