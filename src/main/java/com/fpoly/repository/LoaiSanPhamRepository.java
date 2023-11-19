package com.fpoly.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpoly.entity.LoaiSanPham;

@Repository
public interface LoaiSanPhamRepository extends JpaRepository<LoaiSanPham,Long> {
	@Query(value = "SELECT * FROM `loai_san_pham` c WHERE c.da_xoa = false ORDER BY c.id DESC", nativeQuery = true)
	List<LoaiSanPham> selectAllLoaiSanPhamExist();
	
	@Query(value = "SELECT * FROM `loai_san_pham` c WHERE c.da_xoa = false ORDER BY c.id DESC", nativeQuery = true)
	Page<LoaiSanPham> selectAllLoaiSanPhamExist(Pageable page);
	
	@Query(value = "SELECT * FROM `loai_san_pham` c WHERE c.da_xoa = false AND c.ten_loai_san_pham like %:tenLoaiSanPham% ORDER BY c.id DESC", nativeQuery = true)
	Page<LoaiSanPham> getLoaiSanPhamExistByName(@Param("tenLoaiSanPham") String tenLoaiSanPham, Pageable page);
}
