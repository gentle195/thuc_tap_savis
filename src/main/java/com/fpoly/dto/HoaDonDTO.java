package com.fpoly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoaDonDTO extends BaseDTO<HoaDonDTO> {

    private Long khuyenMaiId;

    private Long nguoiDungId;

    private Long khachHangId;

    private Long giaoDichId;

    private String nguoiNhan;

    private String sdtNguoiNhan;

    private String diaChiGiaoHang;

    private String thoiGianGiaoHang;

    private String ghiChu;

    private BigDecimal tongTienHoaDon;

    private BigDecimal tienShip;

    private BigDecimal tongTienDonHang;

    private int loaiDonHang;
    
    private String maDonHang ;

    private String tenKhuyenMai ;
    
    private Integer page ; 
    
    private Integer totalPages ;
    
    private Integer totalItems ;

    private long trangThaiId;
    
    private List<HoaDonDTO> listHoaDonDTO = new ArrayList<HoaDonDTO>();

    private String duLieuTimKiem;

    private String trangThai;

    private String input = "" ;

    private int limit ;

    private String maDon;

}
