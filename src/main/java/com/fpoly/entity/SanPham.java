package com.fpoly.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "san_pham")
@EntityListeners(AuditingEntityListener.class)
public class SanPham extends BaseEntity implements Serializable{
	@ManyToOne
	@JoinColumn(name = "kieu_dang_id", nullable = false)
	private KieuDang kieuDang;

	@ManyToOne
	@JoinColumn(name = "chat_lieu_id", nullable = false)
	private ChatLieu chatLieu;

	@ManyToOne
	@JoinColumn(name = "loai_san_pham_id", nullable = false)
	private LoaiSanPham loaiSanPham;

	@ManyToOne
	@JoinColumn(name = "phong_cach_id", nullable = false)
	private PhongCach phongCach;

	@OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<HinhAnh> hinhAnhs;

	@Column
	private Boolean daXoa;

	@OneToMany(mappedBy = "sanPham",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SanPhamChiTiet> sanPhamChiTiets = new ArrayList<SanPhamChiTiet>();

	@Column(columnDefinition = "nvarchar(256)", nullable = false)
	@NotEmpty(message = "Tên sản phẩm không được để trống")
	private String tenSanPham;

	@Column(columnDefinition = "nvarchar(512)", nullable = true)
	private String moTa;

	@Column(precision = 10)
	private BigDecimal gia;
}