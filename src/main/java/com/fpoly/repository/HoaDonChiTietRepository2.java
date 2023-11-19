package com.fpoly.repository;

import com.fpoly.entity.HoaDon;
import com.fpoly.entity.HoaDonChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonChiTietRepository2 extends PagingAndSortingRepository<HoaDonChiTiet, Long> {
    @Query(value = "select * from hoa_don_chi_tiet where hoa_don_id = ? and da_xoa = false", nativeQuery = true)
    Page<HoaDonChiTiet> findHDCTByHoaDonId(Long hoaDonId, Pageable pageable);

    @Query(value = "select * from hoa_don_chi_tiet where hoa_don_id = ? and da_xoa = false", nativeQuery = true)
    List<HoaDonChiTiet> findHDCT(Long hoaDonId);

    @Query(value = "select * from hoa_don_chi_tiet where hoa_don_id = ? and da_xoa = true", nativeQuery = true)
    List<HoaDonChiTiet> findHDCT2(Long hoaDonId);
}
