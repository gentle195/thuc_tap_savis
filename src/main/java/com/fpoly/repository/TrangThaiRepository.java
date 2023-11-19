package com.fpoly.repository;

import com.fpoly.entity.TrangThai;
import com.fpoly.entity.VaiTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrangThaiRepository extends JpaRepository<TrangThai, Long> {
    @Query(value = "SELECT * FROM trang_thai WHERE name= :name", nativeQuery = true)
    TrangThai findByTenTrangThai(@Param("name") String name);
}