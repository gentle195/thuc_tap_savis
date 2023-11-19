package com.fpoly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraHangDTO extends BaseDTO<TraHangDTO> {

    private Long khachHangId;

    private String ngayTra;

    private BigDecimal tongTien;

    private String ghiChu;

    private Long lyDoTraHang;

    
}
