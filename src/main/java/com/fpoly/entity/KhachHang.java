package com.fpoly.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "khach_hang")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper=false)
public class KhachHang extends BaseEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "email",columnDefinition = "nvarchar(256)  null ")
    private String email;

    @Column(name = "mat_Khau",columnDefinition = "nvarchar(256) null ")
    private String matKhau;

    @Column(name = "ho_ten",columnDefinition = "nvarchar(256) null ")
    private String hoTen;
    
    @Column(name = "so_lan_mua",columnDefinition = "int null ")
    private int soLanMua;

    @Column(name = "so_dien_thoai",columnDefinition = "nvarchar(256) null ")
    private String soDienThoai;

    @Column(name = "trang_thai",columnDefinition = "int null ")
    private int trangThai;
    
    @OneToMany(mappedBy="khachHang")
    private List<DiaChi> listDiaChi = new ArrayList<DiaChi>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dia_chi_id",nullable=true)
    private DiaChi diaChi;

    @Enumerated(EnumType.STRING)
    @Column(name="auth_provider", columnDefinition = "nvarchar(25) null")
    private AuthenticationProvider authProvider ;

    @Override
    public String toString() {
        return "KhachHang{" +
                "email='" + email + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", soLanMua=" + soLanMua +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", trangThai=" + trangThai +
                ", listDiaChi=" + listDiaChi +
                ", diaChi=" + diaChi.getId() +
                '}';
    }
}
