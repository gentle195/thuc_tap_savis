package com.fpoly.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GioHangDTO extends BaseDTO<GioHangDTO> {

    private Long khachHangId;

    private int trangThai;

    private int tongTien;

    private String maGiamGia;

    private Integer soTienGiamGia;

    private int thanhTien;

    private List<GioHangChiTietDTO> listGioHangChiTiets = new ArrayList<GioHangChiTietDTO>();

    public List<Long> getSelectedCartItemIds() {
        List<Long> selectedCartItemIds = new ArrayList<>();
        for (GioHangChiTietDTO gioHangChiTietDTO : listGioHangChiTiets) {
            if (gioHangChiTietDTO.isChecked()) {
                selectedCartItemIds.add(gioHangChiTietDTO.getId());
            }
        }
        return selectedCartItemIds;
    }

}
