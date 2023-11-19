package com.fpoly.service;

import com.fpoly.dto.BestSellerDTO;
import com.fpoly.dto.DoanhSoChart;
import com.fpoly.dto.DoanhSoDTO;

import java.time.LocalDate;
import java.util.List;

public interface ThongKeService {
    List<DoanhSoChart> getDoanhSoChart(LocalDate startDate, LocalDate endDate);
    DoanhSoDTO getDoanhSoData();
    DoanhSoDTO getDoanhSoDataDate(LocalDate startDate, LocalDate endDate);
    List<BestSellerDTO> getMatHangBanChay();
}
