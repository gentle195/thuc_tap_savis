package com.fpoly.controller.customer.MuaNgay;

import com.fpoly.dto.composite.ShopDetailsDTO;
import com.fpoly.entity.HinhAnh;
import com.fpoly.entity.KichCo;
import com.fpoly.entity.MauSac;
import com.fpoly.entity.SanPham;
import com.fpoly.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductDetaisMuaNgayController {
    @Autowired
    SanPhamService sanPhamService;

    @Autowired
    SanPhamChiTietService sanPhamChiTietService;

    @Autowired
    HinhAnhService hinhAnhService;

    @Autowired
    KichCoService kichCoService;

    @Autowired
    MauSacService mauSacService;

    @RequestMapping("MuaNgay/shop-details/{id}")
    public String shopDetails(Model model, @PathVariable("id") Long sanPhamId) {
        Optional<SanPham> optSP = sanPhamService.findById(sanPhamId);
        if (optSP.isPresent()) {
            ShopDetailsDTO dto = new ShopDetailsDTO();
            List<Long> mauSacIds = sanPhamChiTietService.getLstMauSacBySanPhamId(sanPhamId);
            List<HinhAnh> lstHinhAnh = hinhAnhService.getHinhAnhBySanPhamIdAndMauSacIds(sanPhamId, mauSacIds);
            List<String> lstHinhAnhStr = new ArrayList<>();

            for (HinhAnh ha : lstHinhAnh) {
                lstHinhAnhStr.add(ha.getTenAnh());
            }
            if (lstHinhAnhStr.size() < mauSacIds.size()) {
                for (int i = 0; i < mauSacIds.size() - lstHinhAnhStr.size() - 1; i++) {
                    lstHinhAnhStr.add("default.png");
                }
            }
            if(lstHinhAnhStr.size()%2==0)
            {
                dto.setAnhChinhs1(lstHinhAnhStr.subList(0, lstHinhAnhStr.size()/2));
                dto.setAnhChinhs2(lstHinhAnhStr.subList(lstHinhAnhStr.size()/2, lstHinhAnhStr.size()));
            }
            else
            {
                dto.setAnhChinhs1(lstHinhAnhStr.subList(0, lstHinhAnhStr.size()/2+1));
                dto.setAnhChinhs2(lstHinhAnhStr.subList(lstHinhAnhStr.size()/2+1, lstHinhAnhStr.size()));
            }
            dto.setAnhChinhs(lstHinhAnhStr);
            dto.setSanPhamId(sanPhamId);
            dto.setGia(optSP.get().getGia());
            dto.setTenSanPham(optSP.get().getTenSanPham());
            dto.setMoTa(optSP.get().getMoTa());
            dto.setSoLuong(1);
            List<KichCo> lstKichCo = kichCoService.selectAllKichCoBySanPhamId(sanPhamId);
            List<MauSac> lstMauSac = mauSacService.getAllMauSacExistBySPId(sanPhamId);
            dto.setLstKichCo(lstKichCo);
            dto.setLstMauSac(lstMauSac);
            model.addAttribute("shopDetails", dto);
        }

        return "customer/MuaNgay/MuaNgayProductsDetails";
    }
}
