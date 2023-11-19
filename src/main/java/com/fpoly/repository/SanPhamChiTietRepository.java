package com.fpoly.repository;

import com.fpoly.dto.BestSellerDTO;
import com.fpoly.entity.SanPhamChiTiet;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet,Long>, SanPhamChiTietSearchRepository {
	@Query(value = "SELECT * FROM san_pham_chi_tiet c WHERE c.da_xoa = false", nativeQuery = true)
	List<SanPhamChiTiet> getLstSanPhamChiTietExist();

	@Query(value = "SELECT so_luong FROM san_pham_chi_tiet WHERE id = :id AND da_xoa = false", nativeQuery = true)
	BigDecimal soLuongSPCT(@Param("id") Long id);

	@Query(value = "SELECT * FROM san_pham_chi_tiet s WHERE s.da_xoa = false AND s.san_pham_id = :id ORDER BY s.mau_sac_id", nativeQuery = true)
	List<SanPhamChiTiet> getLstSanPhamChiTietBySanPhamId(@Param("id") Long id);
	
	@Query(value = "SELECT DISTINCT s.* FROM san_pham_chi_tiet s WHERE s.san_pham_id = :id AND s.da_xoa = false", nativeQuery = true)
	List<SanPhamChiTiet> getLstSanPhamChiTietAddImg(@Param("id") Long id);

	@Query(value = "SELECT s1.* FROM san_pham_chi_tiet s1 where s1.da_xoa = false and s1.co_hien_thi = true order by s1.id", nativeQuery = true)
	Page<SanPhamChiTiet> getAllProductDetailIsShowTrue(Pageable pageable);
	
	@Query(value = "SELECT DISTINCT s.mau_sac_id FROM san_pham_chi_tiet s WHERE s.san_pham_id = :id AND s.da_xoa = false ORDER BY s.mau_sac_id DESC", nativeQuery = true)
	List<Long> getLstMauSacBySanPhamId(@Param("id") Long sanPhamId);
	
	@Query(value = "SELECT * FROM san_pham_chi_tiet s WHERE s.san_pham_id = :sanPhamId AND s.mau_sac_id = :mauSacId AND s.kich_co_id = :kichCoId AND s.da_xoa = false", nativeQuery = true)
	Optional<SanPhamChiTiet> getSanPhamChiTietByMauSacSizeSanPhamId(@Param("sanPhamId") Long sanPhamId,@Param("mauSacId") Long mauSacId,@Param("kichCoId") Long kichCoId);

	@Query(value = "SELECT spt.so_luong\n" +
			"FROM san_pham_chi_tiet spt\n" +
			"JOIN kich_co kc ON spt.kich_co_id = kc.id\n" +
			"JOIN mau_sac ms ON spt.mau_sac_id = ms.id\n" +
			"WHERE kc.ten_kich_co = :tenKichCo AND ms.id = :mauSacId AND spt.san_pham_id = :sanPhamId AND spt.co_hien_thi = true AND spt.da_xoa = false\n",
			nativeQuery = true)
	Integer laySoLuongSanPhamChiTiet(@Param("tenKichCo") String tenKichCo,
									 @Param("mauSacId") Long mauSacId,
									 @Param("sanPhamId") Long sanPhamId);

	@Query(value = "SELECT spt.so_luong\n" +
			"FROM san_pham_chi_tiet spt\n" +
			"JOIN kich_co kc ON spt.kich_co_id = kc.id\n" +
			"JOIN mau_sac ms ON spt.mau_sac_id = ms.id\n" +
			"WHERE kc.id = :kichCoId AND ms.id = :mauSacId AND spt.san_pham_id = :sanPhamId AND spt.co_hien_thi = true AND spt.da_xoa = false\n",
			nativeQuery = true)
	Integer laySoLuongSanPhamChiTiet2(@Param("kichCoId") Long tenKichCo,
									 @Param("mauSacId") Long mauSacId,
									 @Param("sanPhamId") Long sanPhamId);
	
	@Query(value = "SELECT count(*) from san_pham_chi_tiet spct where spct.mau_sac_id = :mauSacId and spct.kich_co_id = :kichCoId and spct.san_pham_id = :sanPhamId AND spct.da_xoa = false", nativeQuery = true)
	int selectCountSanPhamChiTietDuplicate(@Param("mauSacId") Long mauSacId, @Param("kichCoId") Long kichCoId, @Param("sanPhamId") Long sanPhamId);
	
	@Query(value = "SELECT * from san_pham_chi_tiet spct where spct.mau_sac_id = :mauSacId and spct.kich_co_id = :kichCoId and spct.san_pham_id = :sanPhamId AND spct.da_xoa = false", nativeQuery = true)
	Optional<SanPhamChiTiet> selectSanPhamChiTietDuplicate(@Param("mauSacId") Long mauSacId, @Param("kichCoId") Long kichCoId, @Param("sanPhamId") Long sanPhamId);
	
	@Query(value="SELECT count(*) FROM san_pham_chi_tiet WHERE san_pham_id = :sanPhamId AND mau_sac_id = :mauSacId AND da_xoa = false", nativeQuery = true)
	int getCountSanPhamChiTietExistBySanPhamIdAndMauSacId(@Param("sanPhamId") Long sanPhamId, @Param("mauSacId") Long mauSacId);
	
	@Query(value="SELECT count(*) FROM san_pham_chi_tiet WHERE san_pham_id = :sanPhamId AND da_xoa = false", nativeQuery = true)
	int getCountSanPhamChiTietExistBySanPhamId(@Param("sanPhamId") Long sanPhamId);

	@Query("select new com.fpoly.dto.BestSellerDTO(sp.id, sum(hd.soLuong)) \n" +
			"from SanPhamChiTiet sp \n" +
			"join HoaDonChiTiet hd on sp = hd.sanPhamChiTiet \n" +
			"where hd.hoaDon.id in (:listHoaDon) \n" +
			"group by sp.id \n" +
			"order by sum(hd.soLuong) desc\n ")
	List<BestSellerDTO> layIdSanPhamChiTietBanChay(List<Long> listHoaDon, Pageable pageable);

	@Query("select sp from SanPhamChiTiet sp where sp.id in (:ids) order by field(sp.id, :ids)")
	List<SanPhamChiTiet> laySanPhamChiTietBanChay(List<Long> ids);

	@Query(value="SELECT COALESCE(sum(spct.so_luong),0) FROM san_pham_chi_tiet spct WHERE spct.san_pham_id = :sanPhamId AND spct.da_xoa = false", nativeQuery = true)
	int getSumSoLuongBySanPhamId(@Param("sanPhamId") Long id);

}	
