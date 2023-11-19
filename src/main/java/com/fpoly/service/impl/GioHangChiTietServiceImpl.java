package com.fpoly.service.impl;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;

import com.fpoly.convertor.GioHangChiTietConvertor;
import com.fpoly.convertor.GioHangConvertor;
import com.fpoly.convertor.SanPhamChiTietConvertor;
import com.fpoly.convertor.SanPhamConvertor;
import com.fpoly.dto.GioHangChiTietDTO;
import com.fpoly.entity.GioHang;
import com.fpoly.entity.GioHangChiTiet;
import com.fpoly.repository.GioHangChiTietRepository;
import com.fpoly.service.GioHangChiTietService;
import com.sun.mail.handlers.message_rfc822;

@Service
public class GioHangChiTietServiceImpl implements GioHangChiTietService {

	@Autowired
	private GioHangChiTietRepository gioHangChiTietRepo ;
	
	@Autowired
	private GioHangChiTietConvertor gioHangChiTietConvertor ;
	
	@Autowired
	private SanPhamChiTietConvertor sanPhamChiTietConvertor ;
	@Autowired
	private SanPhamConvertor sanPhamConvertor ;
	
	@Override
	public List<GioHangChiTietDTO> findAllByGioHangId(Long id) {
		List<GioHangChiTietDTO> listGioHangChiTietDTO = new ArrayList<GioHangChiTietDTO>();
		List<GioHangChiTiet> listGioHangChiTiet = new ArrayList<GioHangChiTiet>();
		GioHangChiTietDTO gioHangChiTietDTO = null ;
		try {
			listGioHangChiTiet = gioHangChiTietRepo.findAllByGioHangId(id);
			for (GioHangChiTiet gioHangChiTiet : listGioHangChiTiet) {
				gioHangChiTietDTO = new GioHangChiTietDTO();
				gioHangChiTietDTO = gioHangChiTietConvertor.toDTO(gioHangChiTiet);
				listGioHangChiTietDTO.add(gioHangChiTietDTO);
			}
			return listGioHangChiTietDTO;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return listGioHangChiTietDTO;
	}

	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public void capNhatSoLuongGioHangChiTiet(Long[] ids, Integer[] soLuongs, Integer[] donGias) {
		List<Integer> listSoLuong = toListInterger(soLuongs);
		List<Integer> listDonGia = toListInterger(donGias);
		for (int i = 0; i < ids.length; i++) {
			Long id =  (Long)Array.get(ids, i);
			Integer soLuong = listSoLuong.get((int) i);
			Integer donGia = listDonGia.get((int) i);
			 if(soLuong > 0) {
				 GioHangChiTiet gioHangChiTiet = gioHangChiTietRepo.getOne(id);
				 if(soLuong > gioHangChiTiet.getSoLuong()) {
					 gioHangChiTiet.getSanPhamChiTiet().setSoLuong(gioHangChiTiet.getSanPhamChiTiet().getSoLuong()- (soLuong - gioHangChiTiet.getSoLuong()));
				 }else {
					 gioHangChiTiet.getSanPhamChiTiet().setSoLuong(gioHangChiTiet.getSanPhamChiTiet().getSoLuong() + (gioHangChiTiet.getSoLuong()) - soLuong  );
				 }
				 
				 gioHangChiTiet.setSoLuong(soLuong);
				 gioHangChiTiet.setDonGia(donGia);
				 gioHangChiTiet.setThanhTien(BigDecimal.valueOf(Math.abs(gioHangChiTiet.getSoLuong()*gioHangChiTiet.getDonGia())));
				 gioHangChiTietRepo.save(gioHangChiTiet);
			 }
		}
	}
	public List<Integer> toListInterger(Integer[] integers) {
		List<Integer> list = new ArrayList<>() ;
		for (int i = 0; i < integers.length; i++) {
			Integer integer = integers[i];
			list.add(integer);
		}
		return list ;
	}

	@Override
	@Transactional
	public void capNhatGioHangThanhDaXoaById(Long id) {
		GioHangChiTiet gioHangChiTiet = gioHangChiTietRepo.findById(id).get();
		if(gioHangChiTiet != null) {
			gioHangChiTiet.setDaXoa(true);
			gioHangChiTietRepo.save(gioHangChiTiet);
		}
	}

	@Override
	@Transactional
	public void capNhatTatCaGioHangThanhDaXoaById() {
		List<GioHangChiTiet> list = gioHangChiTietRepo.findAll();
		if (!list.isEmpty()) {
			for (GioHangChiTiet gioHangChiTiet : list) {
				if(gioHangChiTiet.getDaXoa() == false) {
					gioHangChiTiet.setDaXoa(true);
					gioHangChiTietRepo.save(gioHangChiTiet);
				}
			}
		}
	}

	@Override
	public Integer getTongTienByKhachHangID(Long GioHangId) {
		List<GioHangChiTiet> ghct = gioHangChiTietRepo.findAllByGioHangId(GioHangId);
		if(!ghct.isEmpty()) {
				if(gioHangChiTietRepo.tongTien(GioHangId) != null ) {
					return gioHangChiTietRepo.tongTien(GioHangId);
				}
		}else {
			return 0 ;
		}
		return null ;
	}

	@Override
	public GioHangChiTietDTO findById(Long id) {
		GioHangChiTietDTO gioHangChiTietDTO = null ;
		GioHangChiTiet gioHangChiTiet = gioHangChiTietRepo.findById(id).get();
		if(gioHangChiTiet != null) {
			gioHangChiTietDTO = new GioHangChiTietDTO();
			gioHangChiTietDTO = gioHangChiTietConvertor.toDTO(gioHangChiTiet);
			if(gioHangChiTiet.getSanPhamChiTiet() != null) {
				gioHangChiTietDTO.setSanPhamChiTietDTO(sanPhamChiTietConvertor.toDTO(gioHangChiTiet.getSanPhamChiTiet()));
				gioHangChiTietDTO.getSanPhamChiTietDTO().setSanPhamDTO(sanPhamConvertor.toDTO(gioHangChiTiet.getSanPhamChiTiet().getSanPham()));
			}
			return gioHangChiTietDTO;
		}
		return gioHangChiTietDTO;
	}

}
