package com.fpoly.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fpoly.dto.composite.SanPhamProductManageDTO;
import com.fpoly.dto.search.SPAndSPCTSearchDto;
import com.fpoly.entity.SanPham;
import com.fpoly.entity.SanPhamChiTiet;


public interface SanPhamChiTietService {

	List<SanPhamChiTiet> getLstSanPhamChiTietExist();

	Optional<SanPhamChiTiet> findById(Long id);

	List<SanPhamChiTiet> getLstSanPhamChiTietBySanPhamId(Long id);

	Page<SanPhamChiTiet> searchProductDetailExist(SPAndSPCTSearchDto data, Pageable pageable);

	void delete(SanPhamChiTiet entity);

	<S extends SanPhamChiTiet> S save(S entity);

	List<SanPhamChiTiet> getLstSanPhamChiTietAddImg(Long id);

	List<Long> getLstMauSacBySanPhamId(Long sanPhamId);

	Optional<SanPhamChiTiet> getSanPhamChiTietByMauSacSizeSanPhamId(Long sanPhamId, Long mauSacId, Long kichCoId);

	int selectCountSanPhamChiTietDuplicate(Long mauSacId, Long kichCoId, Long sanPhamId);

	int getCountSanPhamChiTietExistBySanPhamIdAndMauSacId(Long sanPhamId, Long mauSacId);

	int getCountSanPhamChiTietExistBySanPhamId(Long sanPhamId);

	int getSumSoLuongBySanPhamId(Long id);

	Optional<SanPhamChiTiet> selectSanPhamChiTietDuplicate(Long mauSacId, Long kichCoId, Long sanPhamId);

	void WritingToExcelProduct(List<SanPhamProductManageDTO> lstDto) throws IOException;

}
