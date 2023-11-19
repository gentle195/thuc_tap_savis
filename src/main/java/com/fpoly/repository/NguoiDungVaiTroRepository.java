package com.fpoly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpoly.entity.NguoiDungVaiTro;

@Repository
public interface NguoiDungVaiTroRepository extends JpaRepository<NguoiDungVaiTro, Long> {
    @Query(value = "select * from nguoidung_vaitro where nguoi_dung_id = ?", nativeQuery = true)
    NguoiDungVaiTro findByNguoiDungId(long id);
}
