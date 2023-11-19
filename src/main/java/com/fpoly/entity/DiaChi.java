package com.fpoly.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dia_chi")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper=false)
public class DiaChi extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1718501696928321803L;

	@Column(name = "dia_chi",columnDefinition = "nvarchar(255) not null")
	private String diaChi;
	
	@Column(name="ho_ten")
	private String hoTen ; 
	
	@Column(name="so_dien_thoai")
	private String soDienThoai ;

	@ManyToOne
	@JoinColumn(name="khach_hang_id")
	private KhachHang khachHang ;

	@Column(name = "la_dia_chi_mac_dinh")
	private boolean laDiaChiMacDinh = false;

	@Override
	public String toString() {
		return "DiaChi{" +
				"diaChi='" + diaChi + '\'' +
				", hoTen='" + hoTen + '\'' +
				", soDienThoai='" + soDienThoai + '\'' +
				", khachHang=" + khachHang.getId() +
				", laDiaChiMacDinh=" + laDiaChiMacDinh +
				'}';
	}
}
