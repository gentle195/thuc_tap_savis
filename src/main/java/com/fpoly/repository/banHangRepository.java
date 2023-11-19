package com.fpoly.repository;

import com.fpoly.entity.SanPham;
import lombok.val;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface banHangRepository extends JpaRepository<SanPham, Long> {
    @Query(value = "SELECT GROUP_CONCAT(DISTINCT ten_mau_sac) AS mausac\n" +
            "FROM san_pham\n" +
            "         JOIN san_pham_chi_tiet spct ON san_pham.id = spct.san_pham_id\n" +
            "         JOIN kich_co kc ON spct.kich_co_id = kc.id\n" +
            "         JOIN mau_sac ms ON spct.mau_sac_id = ms.id\n" +
            "WHERE san_pham_id = ?", nativeQuery = true)
    String getMauSac(Long sanPhamId);

    @Query(value = "SELECT GROUP_CONCAT(DISTINCT ten_kich_co) AS kichco\n" +
            "FROM san_pham\n" +
            "         JOIN san_pham_chi_tiet spct ON san_pham.id = spct.san_pham_id\n" +
            "         JOIN kich_co kc ON spct.kich_co_id = kc.id\n" +
            "         JOIN mau_sac ms ON spct.mau_sac_id = ms.id\n" +
            "WHERE san_pham_id = ?", nativeQuery = true)
    String getKichCo(Long sanPhamId);

    @Query(value = "SELECT san_pham_chi_tiet.id " +
            "FROM san_pham_chi_tiet " +
            "JOIN mau_sac ms ON san_pham_chi_tiet.mau_sac_id = ms.id " +
            "JOIN kich_co kc ON san_pham_chi_tiet.kich_co_id = kc.id " +
            "WHERE kc.ten_kich_co LIKE :tenKichCo " +
            "AND ms.ten_mau_sac LIKE :tenMauSac", nativeQuery = true)
    Long findIdByTenKichCoAndTenMauSac(String tenKichCo, String tenMauSac);

    @Query(value = "SELECT gia FROM san_pham WHERE id = :sanPhamId", nativeQuery = true)
    BigDecimal getDonGia(@Param("sanPhamId") Long sanPhamId);
}
