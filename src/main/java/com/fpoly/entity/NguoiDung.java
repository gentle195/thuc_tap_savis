package com.fpoly.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "nguoi_dung")
@EntityListeners(AuditingEntityListener.class)
public class NguoiDung extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6627502088104297623L;

    @Column(columnDefinition = "nvarchar(255) null")
    private String maNguoiDung;

    @Column(columnDefinition = "nvarchar(255) null")
    private String email;

    @Column(name = "ten_nguoi_dung", columnDefinition = "nvarchar(200) null")
    private String tenNguoiDung;

    @Column(name = "so_dien_thoai", columnDefinition = "nvarchar(20) null")
    private String soDienThoai;

    @Column(name = "mat_khau", columnDefinition = "nvarchar(255) null")
    private String matKhau;

//	@Enumerated(EnumType.STRING)
//	private Role role;

    @Column(name = "trang_thai", columnDefinition = "int default(0)")
    private int trangThai;

    @Column(name = "anh_nhan_vien", columnDefinition = "nvarchar(255) null")
    private String anhNhanVien;

    @Column(name = "dia_chi", columnDefinition = "nvarchar(255) null")
    private String diaChi;

    @Enumerated(EnumType.STRING)
    @Column(name="auth_provider", columnDefinition = "nvarchar(25) null")
    private AuthenticationProvider authProvider ;
    
    @JsonIgnore
    @OneToMany(mappedBy = "nguoiDung", fetch = FetchType.EAGER)
    private List<NguoiDungVaiTro> listNguoiDungVaiTro;

    public String TrangThai2() {
        String TrangThai2;
        if (trangThai == 0) {
            TrangThai2 = "Đang Hoạt Động";
        } else {
            TrangThai2 = "Không Hoạt Động";
        }
        return TrangThai2;
    }

    @Column(name = "da_xoa")
    private Boolean daXoa;

    @JsonIgnore
    @OneToMany(mappedBy = "nguoiDung")
    private List<GiaoDich> giaoDichs = new ArrayList<GiaoDich>();

    @Override
    public String toString() {
        return "NguoiDung{" +
                ", email='" + email + '\'' +
                ", tenNguoiDung='" + tenNguoiDung + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", trangThai=" + trangThai +
                ", daXoa=" + daXoa +
                ", giaoDichs=" + giaoDichs.size() +
                '}';
    }


}
