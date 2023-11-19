package com.fpoly.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpoly.dto.KhachHangDTO;
import com.fpoly.entity.KhachHang;

import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang,Long> {
	
	@Query(value="SELECT * FROM khach_hang  ",nativeQuery=true)
	Page<KhachHang> findAll(Pageable pageable);
	
	@Query(value="SELECT * FROM khach_hang  WHERE trang_thai=:trangThai ",nativeQuery=true)
	Page<KhachHang> findAllByTrangThaiCoPhanTrang(@Param("trangThai") int trangThai,
			Pageable pageable);
	
	@Query(value="SELECT * FROM khach_hang  WHERE  trang_thai =:trangThai "
			+ "AND so_dien_thoai LIKE %:input% "
			+ "OR  email LIKE %:input% "
			+ "OR ho_ten LIKE %:input% "
			,nativeQuery=true)
	Page<KhachHang> findAllByTrangThaiVaSoDienThoaiCoPhanTrang(@Param("trangThai")int trangThai
			,@Param("input") String input 
			, Pageable pageable);
	
	
	@Query(value="SELECT * FROM khach_hang  WHERE email=:email",nativeQuery=true)
	KhachHang findByEmail(@Param("email") String  email);
	
	@Modifying
	@Query(value="UPDATE khach_hang SET trang_thai = 1 WHERE id=:id",nativeQuery=true)
	void capNhatTrangThaiThanhHoatDongTheoMa(@Param("id") long id);
	
	
	@Modifying
	@Query(value="UPDATE khach_hang  SET trang_thai = 0 WHERE id=:id",nativeQuery=true)
	void capNhatTrangThaiThanhKhongHoatDongTheoMa(@Param("id") long id);
	
	
	@Query(value="SELECT * FROM khach_hang  WHERE  so_dien_thoai LIKE %:input% "
			+ "OR  email LIKE %:input% "
			+ "OR ho_ten LIKE %:input% "
			,nativeQuery=true)
	Page<KhachHang> findAllBySoDienThoaiCoPhanTrang(@Param("input")String input 
			, Pageable pageable);

	@Query(value="SELECT count(*) FROM khach_hang  WHERE trang_thai=:trangThai",nativeQuery=true)
	int countByTrangThai(@Param("trangThai") Integer trangThai);

	@Query(value="SELECT count(*) FROM khach_hang  WHERE"
			+ " so_dien_thoai LIKE %:input% "
			+ "OR  email LIKE %:input% "
			+ "OR ho_ten LIKE %:input% "
			,nativeQuery=true)
	int countByInput(@Param("input")String input);

	@Query(value="SELECT count(*) FROM khach_hang  WHERE trang_thai =:trangThai AND "
			+ "so_dien_thoai LIKE %:input%   "
			+ "OR  email LIKE %:input% "
			+ "OR ho_ten LIKE %:input% "
			,nativeQuery=true)
	int countByInputVaTrangThai(@Param("input")String input, @Param("trangThai")Integer trangThai);

	@Query(value="SELECT * FROM khach_hang  WHERE email =:email AND trang_thai =:trangThai ",nativeQuery=true)
	KhachHang findByEmailAndTrangThai(@Param("email")String email, @Param("trangThai") int i);

	@Query(value = "select * from khach_hang where id = ?", nativeQuery = true)
	KhachHang findKhachHangByID(Long id);

	@Query(value = "select * from khach_hang where so_dien_thoai = ?", nativeQuery = true)
	Optional<KhachHang> findKhachHangBySDT(String SDT);
}
