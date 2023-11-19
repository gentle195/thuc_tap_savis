package com.fpoly.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "san_pham_chi_tiet")
@EntityListeners(AuditingEntityListener.class)
public class SanPhamChiTiet extends BaseEntity implements Serializable{
	@Column(nullable = false)
	private String maSanPhamChiTiet;

	@ManyToOne
	@JoinColumn(name = "kich_co_id", nullable = false)
	private KichCo kichCo;

	@ManyToOne
	@JoinColumn(name = "mau_sac_id", nullable = false)
	private MauSac mauSac;

	@ManyToOne
	@JoinColumn(name = "san_pham_id", nullable = false)
	private SanPham sanPham;

	@Column
	private int soLuong;

	@OneToMany(mappedBy = "sanPhamChiTiet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<HoaDonChiTiet> hoaDonChiTiets;

	@Column
	private Boolean coHienThi;

	@Column
	private Boolean daXoa;

	@OneToMany(mappedBy="sanPhamChiTiet")
	private List<GioHangChiTiet> gioHangChiTiet = new ArrayList<GioHangChiTiet>();

	@Override
	public String toString() {
		return "SanPhamChiTiet{" +
				"maSanPhamChiTiet='" + maSanPhamChiTiet + '\'' +
				", sanPham=" + sanPham +
				", soLuong=" + soLuong +
				", hoaDonChiTiets=" + hoaDonChiTiets.size() +
				", daXoa=" + daXoa +
				'}';
	}
}