package com.fpoly.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpoly.entity.KieuDang;

@Repository
public interface KieuDangRepository extends JpaRepository<KieuDang,Long> {
	@Query(value = "SELECT * FROM kieu_dang c WHERE c.da_xoa = false ORDER BY c.id DESC", nativeQuery = true)
	List<KieuDang> selectAllKieuDangExist();

	@Query(value = "SELECT * FROM kieu_dang c WHERE c.da_xoa = false ORDER BY c.id DESC", nativeQuery = true)
	Page<KieuDang> selectAllKieuDangExist(Pageable page);
	
	@Query(value = "SELECT * FROM kieu_dang c WHERE c.da_xoa = false AND c.ten_kieu_dang like %:tenKieuDang% ORDER BY c.id DESC", nativeQuery = true)
	Page<KieuDang> getKieuDangExistByName(@Param("tenKieuDang") String tenKieuDang, Pageable page);
	
}
