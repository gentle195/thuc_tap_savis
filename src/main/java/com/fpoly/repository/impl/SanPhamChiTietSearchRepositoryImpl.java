package com.fpoly.repository.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Repository;


import com.fpoly.QEntityGenarate.QChatLieu;
import com.fpoly.QEntityGenarate.QKichCo;
import com.fpoly.QEntityGenarate.QKieuDang;
import com.fpoly.QEntityGenarate.QLoaiSanPham;
import com.fpoly.QEntityGenarate.QMauSac;
import com.fpoly.QEntityGenarate.QPhongCach;
import com.fpoly.QEntityGenarate.QSanPham;
import com.fpoly.QEntityGenarate.QSanPhamChiTiet;
import com.fpoly.constant.OptionContants;
import com.fpoly.dto.search.SPAndSPCTSearchDto;
import com.fpoly.entity.SanPham;
import com.fpoly.entity.SanPhamChiTiet;
import com.fpoly.repository.SanPhamChiTietSearchRepository;
import com.fpoly.service.TypeHelperService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;

import lombok.AllArgsConstructor;


@Repository
@AllArgsConstructor
public class SanPhamChiTietSearchRepositoryImpl implements SanPhamChiTietSearchRepository{
	private final EntityManager entityManager;
	
	@Autowired
	private TypeHelperService typeHelperService;
	
	@Override
	public Page<SanPhamChiTiet> searchProductDetailExist(SPAndSPCTSearchDto data, Pageable pageable){
		SPAndSPCTSearchDto dataSearch = convertSearchNotNull(data);
		
		JPQLQuery<SanPhamChiTiet> query = new JPAQuery<>(entityManager);
		Querydsl querydsl = new Querydsl(entityManager, new PathBuilderFactory().create(SanPhamChiTiet.class));
		
		BooleanBuilder where = new BooleanBuilder();
		
		QSanPhamChiTiet qSanPhamChiTiet = QSanPhamChiTiet.sanPhamChiTiet;
		QSanPham qSanPham = QSanPham.sanPham;
		QKieuDang qKieuDang = QKieuDang.kieuDang;
		QPhongCach qPhongCach = QPhongCach.phongCach;
		QChatLieu qChatLieu = QChatLieu.chatLieu;
		QMauSac qMauSac = QMauSac.mauSac;
		QKichCo qKichCo = QKichCo.kichCo;
		QLoaiSanPham qLoaiSanPham = QLoaiSanPham.loaiSanPham;
		
		where.and(qSanPhamChiTiet.daXoa.isFalse());
		where.and(qSanPham.daXoa.isFalse());
		
		
		List<Long> lstKieuDang = dataSearch.getKieuDangIds();
		List<Long> lstChatLieu = dataSearch.getChatLieuIds();
		List<Long> lstLoaiSanPham = dataSearch.getLoaiSanPhamIds();
		List<Long> lstPhongCach = dataSearch.getPhongCachIds();
		List<Long> lstKichCo = dataSearch.getKichCoIds();
		List<Long> lstMauSac = dataSearch.getMauSacIds();
		
		where.and(qSanPhamChiTiet.coHienThi.isTrue());
		
		if(!lstKieuDang.isEmpty() && !lstKieuDang.get(0).equals(-1L)) {
			where.and(qSanPham.kieuDang.id.in(lstKieuDang));
		}
		if(!lstChatLieu.isEmpty() && !lstChatLieu.get(0).equals(-1L)) {
			where.and(qSanPham.chatLieu.id.in(lstChatLieu));
		}
		if(!lstPhongCach.isEmpty() && !lstPhongCach.get(0).equals(-1L)) {
			where.and(qSanPham.phongCach.id.in(lstPhongCach));
		}
		if(!lstLoaiSanPham.isEmpty() && !lstLoaiSanPham.get(0).equals(-1L)) {
			where.and(qSanPham.loaiSanPham.id.in(lstLoaiSanPham));
		}
		if(!lstMauSac.isEmpty() && !lstMauSac.get(0).equals(-1L)) {
			where.and(qSanPhamChiTiet.mauSac.id.in(lstMauSac));
		}
		if(!lstKichCo.isEmpty() && !lstKichCo.get(0).equals(-1L)) {
			where.and(qSanPhamChiTiet.kichCo.id.in(lstKichCo));
		}
		
		if(!dataSearch.getTenSanPham().equalsIgnoreCase("-1")) {
			if(StringUtils.isNotEmpty(dataSearch.getTenSanPham())) {
				where.and(qSanPham.tenSanPham.containsIgnoreCase(dataSearch.getTenSanPham()));
			}
		}
		
		if(lstLoaiSanPham.size() != 0 && !lstLoaiSanPham.get(0).equals(-1L)) {
			where.and(qLoaiSanPham.daXoa.isFalse());
			where.and(qSanPham.loaiSanPham.id.in(lstLoaiSanPham));
		}
		
		if(!dataSearch.getNguoiTaoSPCT().equalsIgnoreCase("-1")) {
			if(StringUtils.isNotEmpty(dataSearch.getNguoiTaoSPCT())) {
				where.and(qSanPham.nguoiTao.containsIgnoreCase(dataSearch.getNguoiTaoSPCT()));
			}
		}
		
		if(!dataSearch.getNguoiCapNhatSPCT().equalsIgnoreCase("-1")) {
			if(StringUtils.isNotEmpty(dataSearch.getNguoiCapNhatSPCT())) {
				where.and(qSanPham.nguoiTao.containsIgnoreCase(dataSearch.getNguoiCapNhatSPCT()));
			}
		}
		
		if(dataSearch.getSoLuongMin().intValue() != -1 &&
				dataSearch.getSoLuongMax().intValue() != -1) {
			if(StringUtils.isNotEmpty(dataSearch.getSoLuongMin().toString()) &&
					StringUtils.isNotEmpty(dataSearch.getSoLuongMax().toString())) {
				where.and(qSanPhamChiTiet.soLuong.between(dataSearch.getSoLuongMin(), dataSearch.getSoLuongMax()));
			}
		}
		if(dataSearch.getSoLuongMin().intValue() != -1 &&
				dataSearch.getSoLuongMax().intValue() == -1) {
			where.and(qSanPhamChiTiet.soLuong.goe(dataSearch.getSoLuongMin()));
		}else if(dataSearch.getSoLuongMin().intValue() == -1 &&
				dataSearch.getSoLuongMax().intValue() != -1) {
			where.and(qSanPhamChiTiet.soLuong.loe(dataSearch.getSoLuongMax()));
		}
		
		if(!dataSearch.getGiaMin().toString().equalsIgnoreCase("-1") &&
				!dataSearch.getGiaMax().toString().equalsIgnoreCase("-1") ) {
			if(!dataSearch.getGiaMin().toString().isEmpty() && 
					!dataSearch.getGiaMax().toString().isEmpty()) {
				where.and(qSanPham.gia.between(dataSearch.getGiaMin(), dataSearch.getGiaMax()));
			}
		}
		if(!dataSearch.getGiaMin().toString().equalsIgnoreCase("-1") &&
				dataSearch.getGiaMax().toString().equalsIgnoreCase("-1")) {
			if(!dataSearch.getGiaMin().toString().isEmpty()) {
				where.and(qSanPham.gia.goe(dataSearch.getGiaMin()));
			}
		}else if(dataSearch.getGiaMin().toString().equalsIgnoreCase("-1") &&
				!dataSearch.getGiaMax().toString().equalsIgnoreCase("-1")) {
			if(!dataSearch.getGiaMax().toString().isEmpty()) {
				where.and(qSanPham.gia.loe(dataSearch.getGiaMax()));
			}
		}
		
		//Check validate Date eq epoch
		final Date CheckDate = Date.from(Instant.EPOCH);
//		Date now = Date.from(Instant.now());
		
		if(!dataSearch.getNgayTaoMin().toString().equalsIgnoreCase(""+CheckDate) &&
				!dataSearch.getNgayTaoMax().toString().equalsIgnoreCase(""+CheckDate)) {
			if(!dataSearch.getNgayTaoMin().toString().isEmpty() && 
					!dataSearch.getNgayTaoMax().toString().isEmpty()) {
				where.and(qSanPhamChiTiet.ngayTao.between(dataSearch.getNgayTaoMin(), dataSearch.getNgayTaoMax()));
			}
		}
		//- so sánh ngày
		//- không dùng được -> lí do: kiểu Date có sai số lớn -> Instant is solution
//		if(!dataSearch.getNgayTaoMin().toString().equalsIgnoreCase(""+CheckDate) &&
//				dataSearch.getNgayTaoMax().toString().equalsIgnoreCase(""+CheckDate)) {
//			if(!dataSearch.getNgayTaoMin().toString().isEmpty()) {
//				where.and(qSanPham.ngayTao.between(dataSearch.getNgayTaoMin(), now));
//			}
//		}
		
		
		if(!dataSearch.getNgayCapNhatMin().toString().equalsIgnoreCase(""+CheckDate) &&
				!dataSearch.getNgayCapNhatMax().toString().equalsIgnoreCase(""+CheckDate)) {
			if(!dataSearch.getNgayCapNhatMin().toString().isEmpty() && 
					!dataSearch.getNgayCapNhatMax().toString().isEmpty()) {
				where.and(qSanPhamChiTiet.ngayCapNhat.between(dataSearch.getNgayCapNhatMin(), dataSearch.getNgayCapNhatMax()));
			}
		}
		
//		if(!dataSearch.getNgayCapNhatMin().toString().equalsIgnoreCase(""+CheckDate) &&
//				dataSearch.getNgayCapNhatMax().toString().equalsIgnoreCase(""+CheckDate)) {
//			if(!dataSearch.getNgayCapNhatMin().toString().isEmpty()) {
//				where.and(qSanPham.ngayCapNhat.between(dataSearch.getNgayCapNhatMin(), now));
//			}
//		}
		
		query.select(qSanPhamChiTiet).from(qSanPhamChiTiet)
				.leftJoin(qMauSac).on(qSanPhamChiTiet.mauSac.id.eq(qMauSac.id))
				.innerJoin(qKichCo).on(qSanPhamChiTiet.kichCo.id.eq(qKichCo.id))
				.innerJoin(qSanPham).on(qSanPhamChiTiet.sanPham.id.eq(qSanPham.id))
				.innerJoin(qPhongCach).on(qSanPham.phongCach.id.eq(qPhongCach.id))
				.innerJoin(qKieuDang).on(qSanPham.kieuDang.id.eq(qKieuDang.id))
				.innerJoin(qChatLieu).on(qSanPham.chatLieu.id.eq(qChatLieu.id))
				.innerJoin(qLoaiSanPham).on(qSanPham.loaiSanPham.id.eq(qLoaiSanPham.id))
				.where(where)
				.orderBy(qSanPhamChiTiet.sanPham.id.desc())
				.fetch();
		List<SanPhamChiTiet> result = querydsl.applyPagination(pageable, query).fetch();
		Long totalElements = query.fetchCount();
		return new PageImpl<SanPhamChiTiet>(result, pageable, totalElements);
	}
	
//	convert type of value not null
//	@author cuongdd3
	public SPAndSPCTSearchDto convertSearchNotNull(SPAndSPCTSearchDto dataSearch) {
		List<Long> lstKieuDangId = typeHelperService.convertObjectTypeListLong(dataSearch.getKieuDangIds());
		List<Long> lstChatLieuId = typeHelperService.convertObjectTypeListLong(dataSearch.getChatLieuIds());
		List<Long> lstLoaiSanPhamId = typeHelperService.convertObjectTypeListLong(dataSearch.getLoaiSanPhamIds());
		List<Long> lstPhongCachId = typeHelperService.convertObjectTypeListLong(dataSearch.getPhongCachIds());
		List<Long> lstKichCoId = typeHelperService.convertObjectTypeListLong(dataSearch.getKichCoIds());
		List<Long> lstMauSacId = typeHelperService.convertObjectTypeListLong(dataSearch.getMauSacIds());
		String tenSanPham = typeHelperService.convertObjectTypeString(dataSearch.getTenSanPham());
		BigDecimal giaHienHanhMin = typeHelperService.convertObjectTypeBigDecimal(dataSearch.getGiaMin());
		BigDecimal giaHienHanhMax = typeHelperService.convertObjectTypeBigDecimal(dataSearch.getGiaMax());
		int soLuongMin = typeHelperService.convertObjectTypeListInt(dataSearch.getSoLuongMin());
		int soLuongMax = typeHelperService.convertObjectTypeListInt(dataSearch.getSoLuongMax());
		List<Boolean> coHienThi = typeHelperService.convertObjectTypeListBoolean(dataSearch.getCoHienThi());
		String nguoiTaoSPCT = typeHelperService.convertObjectTypeString(dataSearch.getNguoiTaoSPCT());
		String nguoiCapNhatSPCT = typeHelperService.convertObjectTypeString(dataSearch.getNguoiCapNhatSPCT());
		Date ngayTaoMin = typeHelperService.convertObjectTypeDate(dataSearch.getNgayTaoMin());
		Date ngayTaoMax = typeHelperService.convertObjectTypeDate(dataSearch.getNgayTaoMax());
		Date ngayCapNhatMin = typeHelperService.convertObjectTypeDate(dataSearch.getNgayCapNhatMin());
		Date ngayCapNhatMax = typeHelperService.convertObjectTypeDate(dataSearch.getNgayCapNhatMax());
		String tieuChiGia = typeHelperService.convertObjectTypeString(dataSearch.getTieuChiGia());
		SPAndSPCTSearchDto result = new SPAndSPCTSearchDto(lstKieuDangId, lstChatLieuId, lstLoaiSanPhamId, lstPhongCachId, lstKichCoId, lstMauSacId, tenSanPham, giaHienHanhMin, giaHienHanhMax, soLuongMin, soLuongMax, coHienThi, nguoiTaoSPCT, nguoiCapNhatSPCT, ngayTaoMin, ngayTaoMax, ngayCapNhatMin, ngayCapNhatMax, tieuChiGia, null);
		return result;
	}
}
