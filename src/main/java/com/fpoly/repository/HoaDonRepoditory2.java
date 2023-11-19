package com.fpoly.repository;

import com.fpoly.entity.HoaDon;
import com.fpoly.entity.HoaDonChiTiet;
import com.fpoly.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HoaDonRepoditory2 extends PagingAndSortingRepository<HoaDon, Long> {
    Page<HoaDon> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM hoa_don WHERE loai_hoa_don = :loai and trang_thai_id = 6 and da_xoa = false ORDER BY ngay_tao DESC", nativeQuery = true)
    Page<HoaDon> finHDByLoaiHDTaiQuay(@Param("loai") Integer loai, PageRequest pageable);

    @Query(value = "SELECT * FROM hoa_don WHERE loai_hoa_don = :loai and da_xoa = false ORDER BY ngay_tao DESC", nativeQuery = true)
    Page<HoaDon> finHDByLoaiHD(@Param("loai") Integer loai, PageRequest pageable);

    @Query(value = "SELECT * FROM hoa_don WHERE trang_thai_id = ?1 and loai_hoa_don = 0 and da_xoa = false ORDER BY ngay_tao DESC", countQuery = "SELECT COUNT(*) FROM hoa_don WHERE trang_thai_id = ?1", nativeQuery = true)
    Page<HoaDon> findByTrangThaiHoaDonListTrangThai(int trangThai, Pageable pageable);

    @Query(value = "SELECT * FROM hoa_don WHERE trang_thai_id = ? and khach_hang_id = ?", nativeQuery = true)
    Page<HoaDon> findHoaDonByTrangThaiAndKhachHangId(int trangThai, Long khachHangId, Pageable pageable);

    @Query(value = "SELECT * FROM hoa_don WHERE trang_thai_id = ? ORDER BY ngay_tao DESC", nativeQuery = true)
    Page<HoaDon> findHoaDonbyId(int trangThai, Pageable pageable);

    @Query(value = "SELECT * FROM hoa_don WHERE loai_hoa_don = :loai", nativeQuery = true)
    Page<HoaDon> finHDByLoaiHD(@Param("loai") Integer loai, Pageable pageable);

    @Query(value = "select * from hoa_don where id = ?", nativeQuery = true)
    Page<HoaDon> findByIDD(@Param("id") Long id, Pageable pageable);

    @Query(value = "select * from hoa_don where trang_thai_id = 7 and loai_hoa_don = 1 and da_xoa = false order by ngay_tao desc", nativeQuery = true)
    Page<HoaDon> findHoaDonDaThanhToan(Pageable pageable);

    @Query(value = "select * from hoa_don where trang_thai_id = 8 and loai_hoa_don = 1 and da_xoa = false order by ngay_tao desc", nativeQuery = true)
    Page<HoaDon> findHoaDonDaHuy(Pageable pageable);

    @Query(value = "SELECT * FROM hoa_don WHERE trang_thai_id = 7 and loai_hoa_don = 1 and da_xoa = false and (ma_don LIKE %:input% OR tong_tien_hoa_don LIKE %:input% OR ghi_chu LIKE %:input%)", nativeQuery = true)
    Page<HoaDon> findAllHoaDonDaThanhToanCoPhanTrang(@Param("input") String input, Pageable pageable);

    @Query(value = "SELECT * FROM hoa_don WHERE trang_thai_id = 7 and loai_hoa_don = 1 and da_xoa = false and DATE(ngay_tao) = :ngayTao", nativeQuery = true)
    Page<HoaDon> findHoaDoDaThanhToanByNgayTao(@Param("ngayTao") LocalDate ngayTao, Pageable pageable);

    @Query(value = "SELECT * FROM hoa_don WHERE trang_thai_id = 8 and loai_hoa_don = 1 and da_xoa = false and (ma_don LIKE %:input% OR tong_tien_hoa_don LIKE %:input% OR ghi_chu LIKE %:input%)", nativeQuery = true)
    Page<HoaDon> findAllHoaDonDaHuyCoPhanTrang(@Param("input") String input, Pageable pageable);

    @Query(value = "SELECT * FROM hoa_don WHERE trang_thai_id = 8 and loai_hoa_don = 1 and da_xoa = false and DATE(ngay_tao) = :ngayTao", nativeQuery = true)
    Page<HoaDon> findHoaDoDaHuyByNgayTao(@Param("ngayTao") LocalDate ngayTao, Pageable pageable);

    @Query(value = "SELECT * FROM hoa_don WHERE trang_thai_id = ?1 AND loai_hoa_don = 0 AND da_xoa = false AND (ma_don LIKE %?2% OR tong_tien_hoa_don LIKE %?2% OR ghi_chu LIKE %?2% OR nguoi_nhan LIKE %?2% OR so_dien_thoai_nguoi_nhan LIKE %?2%)", nativeQuery = true)
    Page<HoaDon> timKiemHoaDonDatHang(int trangThai, String input, Pageable pageable);

    @Query(value = "SELECT * FROM hoa_don WHERE trang_thai_id = ?1 AND loai_hoa_don = 0 AND da_xoa = false AND DATE(ngay_tao) = ?2", nativeQuery = true)
    Page<HoaDon> timKiemHoaDonTheoNgay(int trangThai, LocalDate ngayTao, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM hoa_don WHERE loai_hoa_don = 1 AND trang_thai_id = 6 AND da_xoa = false", nativeQuery = true)
    Integer soLuongHoaDonCho();

    @Query(value = "SELECT * FROM hoa_don WHERE loai_hoa_don = :loai and trang_thai_id = 6 and da_xoa = false ORDER BY ngay_tao DESC", nativeQuery = true)
    List<HoaDon> danhSachHoaDonTaiQuay(@Param("loai") Integer loai);
}
