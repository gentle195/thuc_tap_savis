package com.fpoly.repository;

import com.fpoly.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpoly.entity.NguoiDung;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDung, Long> {
    @Query(value = "select Max(id) from nguoi_dung", nativeQuery = true)
    Integer getMaxId();

    @Query(value = "select * from nguoi_dung where da_xoa = false", nativeQuery = true)
    List<NguoiDung> GetAll();

    @Modifying
    @Query(value = "UPDATE nguoi_dung SET da_xoa = true WHERE id = :id", nativeQuery = true)
    void xoaNguoiDung(@Param("id") Long id);

    @Query(value = "select * from nguoi_dung where trang_thai = ?1;", nativeQuery = true)
    List<NguoiDung> findTrangThai(int trangThai);

    @Query(value = "select * from nguoi_dung where email = ?1 AND da_xoa=false", nativeQuery = true)
    NguoiDung findByEmail(String email);

    @Query(value = "select * from nguoi_dung where email = ?1 AND da_xoa=false", nativeQuery = true)
    Optional<NguoiDung> findByEmail2(String email);

    @Query(value = "select * from nguoi_dung where so_dien_thoai = ?1 AND da_xoa=false", nativeQuery = true)
    NguoiDung findBysoDienThoai(String sDT);

    @Modifying
    @Query(value = "UPDATE nguoi_dung n SET trang_thai = 0 WHERE id=:id", nativeQuery = true)
    void capNhatTrangThaiThanhHoatDongTheoMa(@Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE nguoi_dung n SET trang_thai = 1 WHERE id=:id", nativeQuery = true)
    void capNhatTrangThaiThanhKhongHoatDongTheoMa(@Param("id") Long id);

    @Query(value = "select * from nguoi_dung where trang_thai = 0 AND da_xoa=false AND email =:email", nativeQuery = true)
    NguoiDung findByEmailAndTrangThaiAndDaXoa(@Param("email") String email);
    
    @Query(value = "SELECT * FROM nguoi_dung where da_xoa = false ORDER BY ngay_tao DESC", nativeQuery = true)
    Page<NguoiDung> findAllNguoiDung(Pageable pageable);

    @Query(value = "select * from nguoi_dung where da_xoa = false and (ma_nguoi_dung like %:input% or so_dien_thoai like %:input% or ten_nguoi_dung like %:input% or email like %:input%)", nativeQuery = true)
    Page<NguoiDung> timKiemNguoiDung(@Param("input") String input, Pageable pageable);

    @Query(value = "select * from nguoi_dung where da_xoa = false and DATE(ngay_tao) = :ngayTao", nativeQuery = true)
    Page<NguoiDung> timKiemNguoiDungByNgayTao(@Param("ngayTao") LocalDate ngayTao, Pageable pageable);

    @Query(value = "select * from nguoi_dung where da_xoa = false and trang_thai = ?", nativeQuery = true)
    Page<NguoiDung> timKiemNguoiDungByTrangThai(int trangThai, Pageable pageable);

    @Query(value = "select * from nguoi_dung where ma_nguoi_dung = :maNguoiDung", nativeQuery = true)
    NguoiDung findNguoiDungByMaNguoiDung(@Param("maNguoiDung") String maNguoiDung);
}
