package com.fpoly.entity;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat_lieu")
@EntityListeners(AuditingEntityListener.class)
public class ChatLieu extends BaseEntity implements Serializable {
	
	
	@Column(name = "ten_chat_lieu",columnDefinition = "nvarchar(30) not null")
	private String tenChatLieu;
	
	@Column(name = "da_xoa")
	private Boolean daXoa;
	
	@OneToMany(mappedBy = "chatLieu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<SanPham> sanPhams;	
}
