package com.fpoly.repository;

import com.fpoly.entity.GioHangChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpoly.entity.GioHang;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang,Long> {
	
	@Query("SELECT g FROM GioHang g WHERE g.khachHang.email=?1 ")
	GioHang findGioHangByEmail(String email);
	
	@Query("SELECT g FROM GioHang g WHERE g.khachHang.id=?1 ")
	GioHang findGioHangByKhachHangId(Long id);

	@Query(value = "select * from gio_hang_chi_tiet where gio_hang_id = ?", nativeQuery = true)
	Page<GioHangChiTiet> getGHCTByHDID(Long id, Pageable pageable);

	@Query(value = "select * from gio_hang where khach_hang_id = ?", nativeQuery = true)
	GioHang findGioHangsByKhachHangId(Long id);
}
