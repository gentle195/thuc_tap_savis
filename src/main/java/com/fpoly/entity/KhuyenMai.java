package com.fpoly.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "giam_gia")
@Builder
public class KhuyenMai extends BaseEntity implements Serializable {

    @Column(name = "ten_khuyen_mai",columnDefinition = "nvarchar(256) not null unique")
    private String tenKhuyenMai;

    @Column(name = "ngay_bat_dau",columnDefinition = "Date not null")
    private Date ngayBatDau;

    @Column(name = "ngay_ket_thuc",columnDefinition = "Date not null")
    private Date ngayKetThuc;

    @Column(name = "phan_tram_giam",columnDefinition = "int not null")
    private int phanTramGiam;

    @Column(name = "gia_tri_toi_thieu",columnDefinition = "int not null")
    private int giaTriToiThieu;

    @Column(name = "trang_thai", columnDefinition = "boolean default true")
    private boolean trangThai;

    @Column(name = "xoa", columnDefinition = "Bit")
    private boolean xoa;


}