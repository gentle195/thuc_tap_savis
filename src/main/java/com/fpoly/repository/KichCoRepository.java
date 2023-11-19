package com.fpoly.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpoly.entity.KichCo;

@Repository
public interface KichCoRepository extends JpaRepository<KichCo,Long> {
	@Query(value = "SELECT * FROM kich_co c WHERE c.da_xoa = false ORDER BY c.id DESC", nativeQuery = true)
	List<KichCo> selectAllKichCoExist();
	
	@Query(value = "SELECT * FROM kich_co c WHERE c.da_xoa = false ORDER BY c.id DESC", nativeQuery = true)
	Page<KichCo> selectAllKichCoExist(Pageable pageable);
	
	@Query(value = "SELECT * FROM kich_co c WHERE c.da_xoa = false AND c.ten_kich_co like %:tenKichCo% ORDER BY c.id DESC", nativeQuery = true)
	Page<KichCo> getKichCoExistByName(@Param("tenKichCo") String tenKichCo, Pageable pageable);

	@Query(value = "SELECT  c.* FROM kich_co c join san_pham_chi_tiet s1 on s1.kich_co_id = c.id WHERE c.da_xoa = false and s1.san_pham_id = :sanPhamId group by c.id", nativeQuery = true)
	List<KichCo> selectAllKichCoBySanPhamId(@Param("sanPhamId") Long sanPhamId);
	
}
