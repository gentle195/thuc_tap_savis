package com.fpoly.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lich_su_hoa_don")
public class lichSuHoaDon extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "hoa_don_id")
    private HoaDon hoaDon;

    @Column(name = "nguoi_thao_tac", columnDefinition = "nvarchar(255) null")
    private String nguoiThaoTac;

    @Column(name = "thao_tac",columnDefinition = "nvarchar(255) null")
    private String thaoTac;

}
