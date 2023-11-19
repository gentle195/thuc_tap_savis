package com.fpoly.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpoly.entity.SanPham;


@Repository
@Primary
public interface SanPhamRepository extends JpaRepository<SanPham,Long>, SanPhamSearchRepository {
	@Query(value = "SELECT * FROM san_pham s WHERE s.da_xoa = false ORDER BY s.id", nativeQuery = true)
	Page<SanPham> getSanPhamExist(Pageable pageable);
	
	@Query(value = "SELECT s.* FROM `san_pham` s join `san_pham_chi_tiet` s1 on s.`id` = s1.`san_pham_id` WHERE s.`da_xoa` = false AND s1.`co_hien_thi` = true  group by s.`id` ORDER BY s.`id` DESC", nativeQuery = true)
	Page<SanPham> showSanPhamExistHomePage(Pageable pageable);
	
	@Query(value = "select count(*) from `san_pham` s left join `loai_san_pham` l on s.loai_san_pham_id = l.id where s.loai_san_pham_id = :loaiSanPhamId and l.da_xoa = false and s.da_xoa = false", nativeQuery = true)
	int selectCountSanPhamByLoaiSanPhamId(@Param("loaiSanPhamId") Long loaiSanPhamId);
	
	@Query(value = "select count(*) from `san_pham` s left join `phong_cach` p on s.phong_cach_id = p.id where s.phong_cach_id = :phongCachId and p.da_xoa = false and s.da_xoa = false", nativeQuery = true)
	int selectCountSanPhamByPhongCachId(@Param("phongCachId") Long phongCachId);
	
	@Query(value = "select count(*) from `san_pham` s left join `chat_lieu` p on s.chat_lieu_id = p.id where s.chat_lieu_id = :chatLieuId and p.da_xoa = false and s.da_xoa = false", nativeQuery = true)
	int selectCountSanPhamByChatLieuId(@Param("chatLieuId") Long chatLieuId);
}
