package com.fpoly.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hinh_anh")
public class HinhAnh extends BaseEntity implements Serializable{

	@ManyToOne
	@JoinColumn(name = "mau_sac_id")
	private MauSac mauSac;

	@ManyToOne
	@JoinColumn(name = "san_pham_id")
	private SanPham sanPham;

	@Column(name = "ten_anh", columnDefinition = "nvarchar(200) not null")
	private String tenAnh;

	@Column(name = "la_anh_chinh")
	private Boolean laAnhChinh;

	@Column(name = "co_hien_thi")
	private Boolean coHienThi;
}