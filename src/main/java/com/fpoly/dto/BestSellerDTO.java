package com.fpoly.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BestSellerDTO {
    private Long id;
    private String tenSanPham;
    private String anhChinhs;
    private Double giaBan;
    private Long doanhSo;
    private String phongCach;
    private String kieuDang;
    private String chatLieu;
    private String kichCo;
    private String mauSac;

    public BestSellerDTO(Long id, Long doanhSo){
        this.id = id;
        this.doanhSo = doanhSo;
        this.anhChinhs = "";
        this.tenSanPham = "";
        this.giaBan = 0D;
    }
}
