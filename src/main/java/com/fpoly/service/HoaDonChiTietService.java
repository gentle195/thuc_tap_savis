package com.fpoly.service;

import com.fpoly.convertor.HoaDonChiTietConvertor;
import com.fpoly.convertor.HoaDonConvertor;
import com.fpoly.convertor.SanPhamChiTietConvertor;
import com.fpoly.convertor.SanPhamConvertor;
import com.fpoly.dto.HoaDonChiTietDTO;
import com.fpoly.dto.HoaDonDTO;
import com.fpoly.dto.SanPhamChiTietDTO;
import com.fpoly.dto.HoaDonChiTietDTO;
import com.fpoly.entity.HoaDon;
import com.fpoly.entity.HoaDonChiTiet;
import com.fpoly.repository.HoaDonChiTietRepository;
import com.fpoly.repository.HoaDonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class HoaDonChiTietService {
    private final HoaDonChiTietRepository hoaDonChiTietRepository;
    
    @Autowired
    private HoaDonChiTietConvertor hoaDonChiTietConvertor ;
    
    @Autowired
    private HoaDonRepository hoaDonRepository ;
    
    @Autowired
    private SanPhamChiTietConvertor sanPhamChiTietConvertor ;
    @Autowired
    private SanPhamConvertor sanPhamConvertor ;
    @Autowired
    private HoaDonConvertor hoaDonConvertor ;
    
    @Autowired
    public HoaDonChiTietService(HoaDonChiTietRepository hoaDonChiTietRepository) {
        this.hoaDonChiTietRepository = hoaDonChiTietRepository;
    }

	public HoaDonChiTietDTO findByHoaDonId(String  maDonHang, Pageable pageable) {
		List<HoaDonChiTietDTO> listHoaDonChiTietDTO = new ArrayList<HoaDonChiTietDTO>();
		List<HoaDonChiTiet> listHoaDonChiTiet = new ArrayList<HoaDonChiTiet>();
		HoaDonChiTietDTO hoaDonChiTietDTO = null ;
		if(maDonHang != null) {
			HoaDon hoaDon = hoaDonRepository.findByMaDonHang(maDonHang);
			hoaDonChiTietDTO = new HoaDonChiTietDTO();
			hoaDonChiTietDTO.setPage(pageable.getPageNumber()+1);
			hoaDonChiTietDTO.setTotalItems(hoaDonChiTietRepository.countByHoaDonId(hoaDon.getId()));
			hoaDonChiTietDTO.setTotalPages((int) Math.ceil((double) hoaDonChiTietDTO.getTotalItems() / pageable.getPageSize()));
			listHoaDonChiTiet = hoaDonChiTietRepository.findAllByHoaDonId(hoaDon.getId(),pageable).getContent();
			if(listHoaDonChiTiet != null) {
				for (HoaDonChiTiet hoaDonChiTiet : listHoaDonChiTiet) {
					hoaDonChiTietDTO = new HoaDonChiTietDTO();
					hoaDonChiTietDTO = hoaDonChiTietConvertor.toDTO(hoaDonChiTiet);
					
					//setSanPhamChiTietDTO and SanPhamDTO of hoaDonChiTietDTO
					SanPhamChiTietDTO sanPhamChiTietDTO = sanPhamChiTietConvertor.toDTO(hoaDonChiTiet.getSanPhamChiTiet());
					sanPhamChiTietDTO.setSanPhamDTO(sanPhamConvertor.toDTO(hoaDonChiTiet.getSanPhamChiTiet().getSanPham()));
					hoaDonChiTietDTO.setSanPhamChiTietDTO(sanPhamChiTietDTO);
					
					//add SanPhamChiTietDTO , SanPhamDTO , HoaDonDTO
					listHoaDonChiTietDTO.add(hoaDonChiTietDTO);
				}
				hoaDonChiTietDTO.setListHoaDonChiTietDTO(listHoaDonChiTietDTO);
				return hoaDonChiTietDTO;
			}
		}
		return null ;
	}
}
