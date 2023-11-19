package com.fpoly.repository;

import com.fpoly.entity.lichSuHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LichSuHoaDonRepository extends JpaRepository<lichSuHoaDon,Long> {
    @Query(value = "select * from lich_su_hoa_don where hoa_don_id = ?", nativeQuery = true)
    List<lichSuHoaDon> findLichSuByHDID(Long hoaDonId);
}
