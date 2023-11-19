package com.fpoly.repository;

import java.util.List;
import java.util.Optional;

import com.fpoly.entity.HoaDonChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpoly.entity.GioHangChiTiet;

import javax.transaction.Transactional;


@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet,Long> {
	
	@Query("SELECT ghct FROM GioHangChiTiet ghct WHERE ghct.gioHang.id =:id")
	public List<GioHangChiTiet> findAllByGioHangId(@Param("id") Long id);

	@Query("SELECT ghct FROM GioHangChiTiet ghct WHERE ghct.gioHang.id =:id")
	public Optional<GioHangChiTiet> findByGioHangId(@Param("id") Long id);

	@Query("SELECT SUM(ghct.thanhTien) FROM GioHangChiTiet ghct WHERE ghct.gioHang.id =:id")
	public Integer tongTien(@Param("id")Long id);

	@Query(value = "select * from gio_hang_chi_tiet where gio_hang_id = ?", nativeQuery = true)
	Page<GioHangChiTiet> getGHCTByHDID(Long id, Pageable pageable);

	@Transactional
	@Modifying
	@Query(value = "update gio_hang_chi_tiet set da_xoa = true where san_pham_chi_tiet_id = :id", nativeQuery = true)
	void xoaGioHangChiTiet(@Param("id") Long id);

	@Query(value = "select * from gio_hang_chi_tiet where san_pham_chi_tiet_id = ? and gio_hang_id = ? and da_xoa = false", nativeQuery = true)
	Optional<GioHangChiTiet> findBySanPhamChiTietAndGioHang(long sanPhamCTId, long gioHangId);

	@Query(value = "select * from gio_hang_chi_tiet where gio_hang_id = ? and da_xoa = false", nativeQuery = true)
	List<GioHangChiTiet> findbyGiohangIdAndDaXoa(long id);
}
