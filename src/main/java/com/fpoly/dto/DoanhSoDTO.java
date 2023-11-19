package com.fpoly.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoanhSoDTO {
    private Long donHangThang;
    private Double doanhSoThang;
    private Long donHangNgay;
    private Double doanhSoNgay;
    private Long hangBanDuocThang;
}
