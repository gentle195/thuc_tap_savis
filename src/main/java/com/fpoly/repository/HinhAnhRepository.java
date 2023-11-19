package com.fpoly.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpoly.entity.HinhAnh;

@Repository
public interface HinhAnhRepository extends JpaRepository<HinhAnh, Long> {
	@Query(value ="SELECT h.* FROM `hinh_anh` h LEFT JOIN mau_sac m ON h.mau_sac_id = m.id LEFT JOIN san_pham s ON h.san_pham_id = s.id WHERE h.san_pham_id = :sanPhamId ORDER BY h.mau_sac_id DESC", nativeQuery = true)
	List<HinhAnh> getLstHinhAnhMauSacBySanPhamId(@Param("sanPhamId") Long sanPhamId);
	
	@Query(value ="SELECT DISTINCT(h.mau_sac_id) FROM `hinh_anh` h LEFT JOIN mau_sac m ON h.mau_sac_id = m.id LEFT JOIN san_pham s ON h.san_pham_id = s.id WHERE h.san_pham_id = :sanPhamId ORDER BY h.mau_sac_id DESC", nativeQuery = true)
	List<Long> getDistinctMauSacInHinhAnhBySanPhamId(@Param("sanPhamId") Long sanPhamId);
	
	@Query(value="SELECT h.* FROM hinh_anh h JOIN san_pham s ON h.san_pham_id = s.id WHERE h.la_anh_chinh = true AND h.co_hien_thi = true AND s.da_xoa = false ORDER BY s.id DESC", nativeQuery = true)
	List<HinhAnh> getHinhAnhChinhExist();
	
	@Query(value="SELECT h.* FROM hinh_anh h WHERE h.ten_anh= :tenAnh  LIMIT 1", nativeQuery = true)
	Optional<HinhAnh> getHinhAnhByName(@Param("tenAnh") String tenAnh);
	
	@Query(value="SELECT h.* FROM hinh_anh h WHERE h.san_pham_id = :sanPhamId AND h.mau_sac_id =:mauSacId AND h.la_anh_chinh = true LIMIT 1", nativeQuery = true)
	Optional<HinhAnh> getHinhAnhChinhBySanPhamIdAndMauSacId(@Param("sanPhamId") Long sanPhamId, @Param("mauSacId") Long mauSacId);
	
	@Query(value="SELECT count(*) FROM hinh_anh h WHERE h.san_pham_id = :sanPhamId AND h.mau_sac_id =:mauSacId AND h.la_anh_chinh = true", nativeQuery = true)
	int getCountHinhAnhChinhBySanPhamIdAndMauSacId(@Param("sanPhamId") Long sanPhamId, @Param("mauSacId") Long mauSacId);
	
	@Query(value="SELECT h.* FROM hinh_anh h WHERE h.san_pham_id= :sanPhamId AND h.co_hien_thi = true AND h.la_anh_chinh = true AND h.mau_sac_id in (:mauSacIds) ORDER BY h.mau_sac_id DESC", nativeQuery = true)
	List<HinhAnh> getHinhAnhChinhBySanPhamIdAndMauSacIds(@Param("sanPhamId") Long sanPhamId, @Param("mauSacIds") List<Long> mauSacIds);
	
	@Query(value="SELECT h.* FROM hinh_anh h WHERE h.san_pham_id= :sanPhamId AND h.co_hien_thi = true AND h.mau_sac_id in (:mauSacIds) ORDER BY h.mau_sac_id DESC", nativeQuery = true)
	List<HinhAnh> getHinhAnhBySanPhamIdAndMauSacIds(@Param("sanPhamId") Long sanPhamId, @Param("mauSacIds") List<Long> mauSacIds);
	
	@Query(value="SELECT h.* FROM hinh_anh h WHERE h.san_pham_id= :sanPhamId AND h.la_anh_chinh = true", nativeQuery = true)
	List<HinhAnh> getHinhAnhChinhBySanPhamId(@Param("sanPhamId") Long sanPhamId);
	
	@Query(value="SELECT h.* FROM hinh_anh h WHERE h.san_pham_id= :sanPhamId", nativeQuery = true)
	List<HinhAnh> getHinhAnhBySanPhamId(@Param("sanPhamId") Long sanPhamId);
	
	@Query(value="SELECT h.* FROM hinh_anh h WHERE h.san_pham_id= :sanPhamId AND h.mau_sac_id = :mauSacId ORDER BY h.id DESC", nativeQuery = true)
	List<HinhAnh> getHinhAnhBySanPhamIdAndMauSacId(@Param("sanPhamId") Long sanPhamId, @Param("mauSacId") Long mauSacId);

	@Query(value="SELECT h.* FROM hinh_anh h WHERE h.mau_sac_id= :mauSacId AND h.san_pham_id= :sanPhamId  AND h.la_anh_chinh = true", nativeQuery = true)
	HinhAnh findByHinhAnhByMauSacIdVaLaAnhChinh(@Param("mauSacId") Long mauSacId,@Param("sanPhamId") Long sanPhamId);

	@Query(value = "SELECT ten_anh FROM hinh_anh WHERE mau_sac_id = ?1 AND san_pham_id = ?2 AND la_anh_chinh = true", nativeQuery = true)
	String findTenAnhChinhByMauSacIdAndSanPhamId(Long mauSacId, Long sanPhamId);

	@Query(value = "SELECT count(*) from hinh_anh  WHERE san_pham_id = :sanPhamId", nativeQuery = true)
	int getCountHinhAnhBySanPhamId(@Param("sanPhamId") Long sanPhamId);
	
	@Query(value = "SELECT sum(9 + (SELECT count(distinct(mau_sac_id)) from san_pham_chi_tiet WHERE san_pham_id = :sanPhamId AND da_xoa = false)) as 'countHinhAnhToiDaDuocThem'", nativeQuery = true)
	int getCountHinhAnhChoPhepThemBySanPhamId(@Param("sanPhamId") Long sanPhamId);
}
