package com.fpoly.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpoly.entity.PhongCach;

@Repository
public interface PhongCachRepository extends JpaRepository<PhongCach, Long> {
	@Query(value = "SELECT * FROM phong_cach c WHERE c.da_xoa = false ORDER BY c.id DESC", nativeQuery = true)
	List<PhongCach> selectAllPhongCachExist();

	@Query(value = "SELECT * FROM phong_cach c WHERE c.da_xoa = false ORDER BY c.id DESC", nativeQuery = true)
	Page<PhongCach> selectAllPhongCachExist(Pageable page);
	
	@Query(value = "SELECT * FROM phong_cach c WHERE c.da_xoa = false AND c.ten_phong_cach like %:tenPhongCach% ORDER BY c.id DESC", nativeQuery = true)
	Page<PhongCach> getPhongCachExistByName(@Param("tenPhongCach") String tenPhongCach, Pageable page);
}
