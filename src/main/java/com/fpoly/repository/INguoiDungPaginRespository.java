package com.fpoly.repository;

import com.fpoly.entity.NguoiDung;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface INguoiDungPaginRespository extends PagingAndSortingRepository<NguoiDung, Long> {
    Page<NguoiDung> findAll(Pageable pageable);

    @Query(value = "select * from nguoi_dung where da_xoa = false", nativeQuery = true)
    Page<NguoiDung> GetAll(Pageable pageable);
}
