package com.fpoly.controller.customer.KhachHang;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fpoly.dto.DiaChiDTO;
import com.fpoly.dto.KhachHangDTO;
import com.fpoly.service.DiaChiService;
import com.fpoly.service.KhachHangService;

@Controller
public class QuanLyTaiKhoanController {
	@Autowired
	private KhachHangService khachHangService ;
	
	@Autowired
	private DiaChiService diaChiService ;
	
	
	@RequestMapping("customer/tai-khoan")
	public String redirect() {
		return "redirect:/customer/quan-ly-tai-khoan?page=1";
	}
	
	@RequestMapping("customer/quan-ly-tai-khoan")
	public String thongTinKhachHang(@RequestParam(name="page",defaultValue="1") Integer page ,Model model ,RedirectAttributes redirectAttributes) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		if(email == null) {
			 redirectAttributes.addFlashAttribute("message","Phiên đăng nhập đã hết hạn !");
			return "redirect:/security/login/form";
		}else {
			Pageable pageable = PageRequest.of(page-1,5, Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
			KhachHangDTO khachHangDTO = khachHangService.findByEmailAndTrangThai(email,1);
			if(khachHangDTO != null) {
				khachHangDTO.setEmail(email);
				khachHangDTO.setCity(email);
			    khachHangDTO.setDistrict(email);
				khachHangDTO.setWard(email);
				khachHangDTO.setSoNha(email);
				khachHangDTO.setLimit(5);
				khachHangDTO.setPage(page);
				khachHangDTO.setTotalItems(diaChiService.countByMaKhachHang(khachHangDTO.getId()));
				khachHangDTO.setTotalPages((int) Math.ceil((double) khachHangDTO.getTotalItems() / khachHangDTO.getLimit()));
				khachHangDTO.setListDiaChiDTO(diaChiService.findAllDiaChiByMaKhachHang(khachHangDTO.getId(),pageable));
				model.addAttribute("khachHangDTO",khachHangDTO);
			}else {
				return "redirect:/security/login/form";
			}
		}
		return "/customer/khach-hang/quan-ly-tai-khoan";
	}
	@PostMapping("customer/quan-ly-tai-khoan/cap-nhat")
	public String chinhSuaThongTin(
			@Valid @ModelAttribute("khachHangDTO") KhachHangDTO khachHangDTO ,BindingResult result ,
			@RequestParam(name="page",defaultValue="1") Integer page 
			,Model model ,RedirectAttributes redirectAttributes) {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			 Pageable pageable = PageRequest.of(page-1,5, Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
			 khachHangDTO.setLimit(5);
			 khachHangDTO.setPage(page);
			 khachHangDTO.setTotalItems(diaChiService.countByMaKhachHang(khachHangDTO.getId()));
			 khachHangDTO.setTotalPages((int) Math.ceil((double) khachHangDTO.getTotalItems() / khachHangDTO.getLimit()));
			 khachHangDTO.setListDiaChiDTO(diaChiService.findAllDiaChiByMaKhachHang(khachHangDTO.getId(),pageable));
			 if(email == null) {
				 redirectAttributes.addFlashAttribute("message","Phiên đăng nhập đã hết hạn !");
				return "redirect:/security/login/form";
			}else {
				if(result.hasErrors()) {
					return "/customer/khach-hang/quan-ly-tai-khoan";
				}
				 khachHangService.save(khachHangDTO);
				 redirectAttributes.addFlashAttribute("message","Cập nhật thành công !");
				 model.addAttribute("khachHangDTO",khachHangDTO);
				 return "redirect:/customer/quan-ly-tai-khoan?page="+khachHangDTO.getPage();
			}
			
	}
	@PostMapping("customer/quan-ly-tai-khoan/cap-nhat-dia-chi")
	public String capNhatDiaChi(@RequestParam("diaChi") String diaChi ,
											@RequestParam("hoTen") String hoTen ,
											@RequestParam("soDienThoai") String soDienThoai,
											@RequestParam("idDiaChi") Long id,
											@RequestParam("idKhachHang") Long idKhachHang
											,RedirectAttributes redirectAttributes
											) {
		DiaChiDTO diaChiDTO = new DiaChiDTO();
		diaChiDTO.setId(id);
		diaChiDTO.setDiaChi(diaChi);
		diaChiDTO.setHoTen(hoTen);
		diaChiDTO.setSoDienThoai(soDienThoai);
		diaChiDTO.setKhachHangId(idKhachHang);
		diaChiService.update(diaChiDTO);
		redirectAttributes.addFlashAttribute("message", "Cập nhật địa chỉ thành công !");
		return "redirect:/customer/quan-ly-tai-khoan?page=1";
	}
}
