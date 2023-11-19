package com.fpoly.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpoly.entity.MauSac;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac,Long> {
	@Query(value = "SELECT * FROM mau_sac c WHERE c.da_xoa = false ORDER BY c.id DESC", nativeQuery = true)
	List<MauSac> selectAllMauSacExist();
	
	@Query(value = "SELECT * FROM mau_sac c WHERE c.da_xoa = false ORDER BY c.id DESC", nativeQuery = true)
	Page<MauSac> selectAllMauSacExist(Pageable page);
	
	@Query(value = "SELECT * FROM mau_sac c WHERE c.da_xoa = false AND c.ten_mau_sac like %:tenMauSac% ORDER BY c.id DESC", nativeQuery = true)
	Page<MauSac> getMauSacExistByName(@Param("tenMauSac") String tenMauSac, Pageable page);

	@Query(value = "SELECT DISTINCT m.* FROM `mau_sac` m LEFT JOIN san_pham_chi_tiet s ON m.id = s.mau_sac_id WHERE s.san_pham_id = :spId AND s.da_xoa = false ORDER BY m.id DESC", nativeQuery = true)
	List<MauSac> getAllMauSacExistBySPId(@Param("spId") Long spId);
	
}
