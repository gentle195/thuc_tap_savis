package com.fpoly.repository;

import com.fpoly.entity.GiaoDich;
import com.fpoly.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HoaDonRepository extends CrudRepository<HoaDon, Long> {

    @Query(value = "SELECT * FROM hoa_don WHERE loai_hoa_don = 1 and da_xoa = false", nativeQuery = true)
    HoaDon findHoaDonBanHang();

    @Query(value = "select * from hoa_don where trang_thai_id = ?1", nativeQuery = true)
    List<HoaDon> findByTrangThaiHoaDonListTrangThai(int trangThai);

    @Query(value = "select Max(id) from hoa_don", nativeQuery = true)
    Integer getMaxId();

    List<HoaDon> findByNgayTao(Date ngayTao);

    @Query(value = "SELECT * FROM hoa_don WHERE loai_hoa_don = :loaiHoaDon AND khach_hang_id = :khachHangId", nativeQuery = true)
    Page<HoaDon> findAllByLoaiHoaDonAndMaKhachHang(@Param("loaiHoaDon") Integer loaiHoaDon,
                                                   @Param("khachHangId") Long khachHangId, Pageable pageable);

    @Query(value = "SELECT count(*) FROM hoa_don WHERE loai_hoa_don = :loaiHoaDon", nativeQuery = true)
    Integer countByLoaiHoaDon(@Param("loaiHoaDon") Integer loaiHoaDon);

    @Query(value = "SELECT * FROM hoa_don WHERE ma_don = :maDonHang", nativeQuery = true)
    HoaDon findByMaDonHang(@Param("maDonHang") String maDonHang);

    @Modifying
    @Query(value = "UPDATE  hoa_don SET trang_thai_id=5 WHERE id = :id", nativeQuery = true)
    void capNhatTrangThaiThanhHuyDon(@Param("id") Long id);

    @Query(value = "select * from giao_dich where trang_thai_id = ? and hoa_don_id = ? ORDER BY id DESC LIMIT 1", nativeQuery = true)
    List<GiaoDich> timeLine(int trangThai, Long hoaDonId);

    @Query("select hd from HoaDon hd where hd.ngayTao <= :endDate and hd.ngayTao >= :startDate")
    List<HoaDon> getHoaDonInRangeDate(Date startDate, Date endDate);
}


