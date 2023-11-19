package com.fpoly.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.util.Date;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity  {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id ;
	
	@Column(name="ngay_tao",updatable=false)
	@CreatedDate
	private Date ngayTao;
	
	@Column(name="nguoi_tao",updatable=false)
	@CreatedBy
	private String nguoiTao;
	
	@Column(name="ngay_cap_nhat",updatable=true)
	@LastModifiedDate
	private Date ngayCapNhat;
	
	@Column(name="nguoi_cap_nhat",updatable=true)
	@LastModifiedBy
	private String nguoiCapNhat;
}
