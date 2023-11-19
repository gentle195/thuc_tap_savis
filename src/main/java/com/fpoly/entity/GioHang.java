package com.fpoly.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gio_hang")
@EqualsAndHashCode(callSuper=false)
public class GioHang extends BaseEntity implements Serializable {
    

    @Column(name = "ngay_tao",columnDefinition = "nvarchar(256)  ")
    @CreatedDate
    private Date ngayTao;

    @Column(name = "ngay_cap_nhat",columnDefinition = "nvarchar(256)  ")
    private Date ngayCapNhat;

    @OneToOne
    @JoinColumn(name = "khach_hang_id")
    private KhachHang khachHang;

    @Column(name = "trang_thai",columnDefinition = "int not null")
    private int trangThai;

    @Column(name = "tong_tien", columnDefinition = "int")
    private int tongTien;
    
    @OneToMany(mappedBy="gioHang")
    private List<GioHangChiTiet> gioHangChiTiets = new ArrayList<GioHangChiTiet>();

    @Override
    public String toString() {
        return "GioHang{" +
                "ngayTao=" + ngayTao +
                ", ngayCapNhat=" + ngayCapNhat +
                ", khachHang=" + khachHang.getId() +
                ", trangThai=" + trangThai +
                ", tongTien=" + tongTien +
                ", gioHangChiTiets=" + gioHangChiTiets.size() +
                '}';
    }
}
