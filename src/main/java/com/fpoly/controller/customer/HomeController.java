package com.fpoly.controller.customer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fpoly.entity.*;
import com.fpoly.repository.GioHangChiTietRepository;
import com.fpoly.repository.GioHangRepository;
import com.fpoly.repository.KhachHangRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fpoly.dto.ChatLieuDTO;
import com.fpoly.dto.KichCoDTO;
import com.fpoly.dto.KieuDangDTO;
import com.fpoly.dto.LoaiSanPhamDTO;
import com.fpoly.dto.MauSacDTO;
import com.fpoly.dto.PhongCachDTO;
import com.fpoly.dto.composite.SPTaiQuayDTO;
import com.fpoly.dto.composite.SanPhamManageDTO;
import com.fpoly.dto.composite.ShopDetailsDTO;
import com.fpoly.dto.composite.ShowSanPhamTaiQuayDTO;
import com.fpoly.dto.search.SPAndSPCTSearchDto;
import com.fpoly.service.ChatLieuService;
import com.fpoly.service.HinhAnhService;
import com.fpoly.service.KichCoService;
import com.fpoly.service.KieuDangService;
import com.fpoly.service.LoaiSanPhamService;
import com.fpoly.service.MauSacService;
import com.fpoly.service.PhongCachService;
import com.fpoly.service.SanPhamChiTietService;
import com.fpoly.service.SanPhamService;
import com.fpoly.service.StorageService;

@Controller
@RequestMapping("/customer")
public class HomeController {
    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private MauSacService mauSacService;

    @Autowired
    private ChatLieuService chatLieuService;

    @Autowired
    private KichCoService kichCoService;

    @Autowired
    private LoaiSanPhamService loaiSanPhamService;

    @Autowired
    private PhongCachService phongCachService;

    @Autowired
    private KieuDangService kieuDangService;

    @Autowired
    private HinhAnhService hinhAnhService;

    @Autowired
    private StorageService storageService;

    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    GioHangRepository gioHangRepository;

    @Autowired
    GioHangChiTietRepository gioHangChiTietRepository;

    @ModelAttribute("lstGia")
    public List<String> getLstGia() {
        List<String> gia = new ArrayList<String>();
        gia.add("0VNĐ - 100.000VNĐ");
        gia.add("100.000VNĐ - 200.000VNĐ");
        gia.add("200.000VNĐ - 300.000VNĐ");
        gia.add("300.000VNĐ - 500.000VNĐ");
        gia.add("500.000VNĐ - 1.000.000VNĐ");
        gia.add("1.000.000VNĐ+");
        return gia;
    }

    @ModelAttribute("lstMauSac")
    public List<MauSacDTO> getLstMauSac() {
        return mauSacService.selectAllMauSacExist().stream().map(item -> {
            MauSacDTO dto = new MauSacDTO();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @ModelAttribute("lstKieuDang")
    public List<KieuDangDTO> getLstKieuDang() {
        return kieuDangService.selectAllKieuDangExist().stream().map(item -> {
            KieuDangDTO dto = new KieuDangDTO();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @ModelAttribute("lstChatLieu")
    public List<ChatLieuDTO> getLstChatLieu() {
        return chatLieuService.selectAllChatLieuExist().stream().map(item -> {
            int soSanPhamCungCL = sanPhamService.selectCountSanPhamByChatLieuId(item.getId());
            ChatLieuDTO dto = new ChatLieuDTO();
            BeanUtils.copyProperties(item, dto);
            dto.setSoSanPhamCungChatLieu(soSanPhamCungCL);
            return dto;
        }).collect(Collectors.toList());
    }

    @ModelAttribute("lstKichCo")
    public List<KichCoDTO> getLstKichCo() {
        return kichCoService.selectAllKichCoExist().stream().map(item -> {
            KichCoDTO dto = new KichCoDTO();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @ModelAttribute("lstLoaiSanPham")
    public List<LoaiSanPhamDTO> getLstLoaiHang() {
        return loaiSanPhamService.selectAllLoaiHangExist().stream().map(item -> {
            int soSanPhamCungLoai = sanPhamService.selectCountSanPhamByLoaiSanPhamId(item.getId());
            LoaiSanPhamDTO dto = new LoaiSanPhamDTO();
            BeanUtils.copyProperties(item, dto);
            dto.setSoSanPhamCungLoai(soSanPhamCungLoai);
            return dto;
        }).collect(Collectors.toList());
    }

    @ModelAttribute("lstPhongCach")
    public List<PhongCachDTO> getLstPhongCach() {
        return phongCachService.selectAllPhongCachExist().stream().map(item -> {
            int soSanPhamCungPC = sanPhamService.selectCountSanPhamByPhongCachId(item.getId());
            PhongCachDTO dto = new PhongCachDTO();
            BeanUtils.copyProperties(item, dto);
            dto.setSoSanPhamCungPhongCach(soSanPhamCungPC);
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("home")
    public String home(Model model) {
        int currentPage = 1;
        int pageSize = 8;
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        SPTaiQuayDTO resultSP = new SPTaiQuayDTO();
        List<ShowSanPhamTaiQuayDTO> lstSSPTQ = new ArrayList<>();
        Page<SanPham> resultPage = sanPhamService.showSanPhamExistHomePage(pageable);
        for (SanPham sp : resultPage.getContent()) {
            ShowSanPhamTaiQuayDTO ssptq = new ShowSanPhamTaiQuayDTO();
            List<Long> mauSacIds = sanPhamChiTietService.getLstMauSacBySanPhamId(sp.getId());
            List<HinhAnh> lstHinhAnh = hinhAnhService.getHinhAnhChinhBySanPhamIdAndMauSacIds(sp.getId(), mauSacIds);
            List<String> lstHinhAnhStr = new ArrayList<>();

            for (HinhAnh ha : lstHinhAnh) {
                lstHinhAnhStr.add(ha.getTenAnh());
            }
            if (lstHinhAnhStr.size() < mauSacIds.size()) {
                for (int i = 0; i < mauSacIds.size() - lstHinhAnhStr.size() - 1; i++) {
                    lstHinhAnhStr.add("default.png");
                }
            }
            ssptq.setAnhChinhs(lstHinhAnhStr);
            ssptq.setSanPhamId(sp.getId());
            ssptq.setGia(sp.getGia());
            ssptq.setTenSanPham(sp.getTenSanPham());
            List<KichCo> lstKichCo = kichCoService.selectAllKichCoBySanPhamId(sp.getId());
            List<MauSac> lstMauSac = mauSacService.getAllMauSacExistBySPId(sp.getId());
            ssptq.setLstKichCo(lstKichCo);
            ssptq.setLstMauSac(lstMauSac);
            lstSSPTQ.add(ssptq);
        }
        resultSP.setLstShowSanPhamTaiQuayDTO(lstSSPTQ);
        model.addAttribute("resultSP", resultSP);

        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);
            if (totalPages > 5) {
                if (end == totalPages) {
                    start = end - 5;
                } else if (start == 1) {
                    end = start + 5;
                }
            }
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("sanPhamPage", resultPage);
        return "customer/view/home";
    }

    @GetMapping("shop")
    public String shop(Model model, @RequestParam("page") Optional<Integer> page,
                       @ModelAttribute(name = "dataSearch") SPAndSPCTSearchDto dataSearch) {
        int currentPage = page.orElse(1);
        int pageSize = 12;
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        SPTaiQuayDTO resultSP = new SPTaiQuayDTO();
        List<ShowSanPhamTaiQuayDTO> lstSSPTQ = new ArrayList<>();
        Page<SanPham> resultPage = sanPhamService.searchProductExist(dataSearch, pageable);
        for (SanPham sp : resultPage.getContent()) {
            ShowSanPhamTaiQuayDTO ssptq = new ShowSanPhamTaiQuayDTO();
            List<Long> mauSacIds = sanPhamChiTietService.getLstMauSacBySanPhamId(sp.getId());
            List<HinhAnh> lstHinhAnh = hinhAnhService.getHinhAnhChinhBySanPhamIdAndMauSacIds(sp.getId(), mauSacIds);
            List<String> lstHinhAnhStr = new ArrayList<>();
//			for (int i = lstHinhAnh.size() - 1; 0 <= i; i--) {
//				lstHinhAnhStr.add(lstHinhAnh.get(i).getTenAnh());
//			}
            for (HinhAnh ha : lstHinhAnh) {
                lstHinhAnhStr.add(ha.getTenAnh());
            }
            if (lstHinhAnhStr.size() < mauSacIds.size()) {
                for (int i = 0; i < mauSacIds.size() - lstHinhAnhStr.size(); i++) {
                    lstHinhAnhStr.add("default.png");
                }
            }
            ssptq.setLoaiSanPhamId(sp.getLoaiSanPham().getId());
            ssptq.setAnhChinhs(lstHinhAnhStr);
            ssptq.setSanPhamId(sp.getId());
            ssptq.setGia(sp.getGia());
            ssptq.setTenSanPham(sp.getTenSanPham());
            List<KichCo> lstKichCo = kichCoService.selectAllKichCoBySanPhamId(sp.getId());
            List<MauSac> lstMauSac = mauSacService.getAllMauSacExistBySPId(sp.getId());
            ssptq.setLstKichCo(lstKichCo);
            ssptq.setLstMauSac(lstMauSac);
            lstSSPTQ.add(ssptq);
        }
        resultSP.setLstShowSanPhamTaiQuayDTO(lstSSPTQ);
        model.addAttribute("resultSP", resultSP);

        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);
            if (totalPages > 5) {
                if (end == totalPages) {
                    start = end - 5;
                } else if (start == 1) {
                    end = start + 5;
                }
            }
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("sanPhamPage", resultPage);
        model.addAttribute("dataSearch", dataSearch);
        return "customer/view/shop";
    }

    @GetMapping("shop-details/{id}")
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

        return "customer/view/shop-details";
    }
}
