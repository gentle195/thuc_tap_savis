package com.fpoly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrangThaiHoaDonDTO extends BaseDTO<TrangThaiHoaDonDTO> {

    private Long hoaDonId;

    private int trangThai;
}
