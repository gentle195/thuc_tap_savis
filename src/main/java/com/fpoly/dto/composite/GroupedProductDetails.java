package com.fpoly.dto.composite;

import com.fpoly.entity.HoaDonChiTiet;

import java.util.List;

public class GroupedProductDetails {
    private Long productId;
    private List<HoaDonChiTiet> productDetails;

    public GroupedProductDetails(Long productId, List<HoaDonChiTiet> productDetails) {
        this.productId = productId;
        this.productDetails = productDetails;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<HoaDonChiTiet> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<HoaDonChiTiet> productDetails) {
        this.productDetails = productDetails;
    }
}
