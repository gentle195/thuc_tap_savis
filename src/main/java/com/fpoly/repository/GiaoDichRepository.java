package com.fpoly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpoly.entity.GiaoDich;

import java.util.List;

@Repository
public interface GiaoDichRepository extends JpaRepository<GiaoDich,Long> {
    @Query(value = "select * from giao_dich where trang_thai_id = ? and hoa_don_id = ? ORDER BY id DESC LIMIT 1", nativeQuery = true)
    List<GiaoDich> findByTrangThaiIdAndHoaDonId(int trangThai, Long hoaDonId);
}
