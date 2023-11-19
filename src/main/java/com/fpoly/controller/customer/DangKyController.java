package com.fpoly.controller.customer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.dto.DiaChiDTO;
import com.fpoly.dto.KhachHangDTO;
import com.fpoly.entity.KhachHang;
import com.fpoly.service.DiaChiService;
import com.fpoly.service.KhachHangService;

@CrossOrigin("*")
@Controller
public class DangKyController {
	
	
	
	@Autowired
	private KhachHangService KhachHangService ;
	
	
	@Autowired
	private DiaChiService diaChiService ;
	
	@RequestMapping("/security/register/create")
	public String registerForm(Model model) {
		model.addAttribute("khachHangDTO",new KhachHangDTO());
		return "/customer/auth/dang-ky";
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@PostMapping("/security/register/create")
	public String register( @Validated @ModelAttribute("khachHangDTO") KhachHangDTO khachHangDTO , BindingResult result ,
			Model md ) {
			if(result.hasErrors()) {
				return "/customer/auth/dang-ky";
			}else {
				if(KhachHangService.findByEmail(khachHangDTO.getEmail())   != null) {
					if(KhachHangService.findByEmailAndTrangThai(khachHangDTO.getEmail(),0) != null) {
						md.addAttribute("messageError","Tài khoản đã bị vô hiệu hóa vui lòng liên hệ 0965221785 !");
						return "/customer/auth/dang-ky";
					}else if(KhachHangService.findByEmailAndTrangThai(khachHangDTO.getEmail(),1) != null) {
						md.addAttribute("messageError","Email đã được đăng ký !");
						return "/customer/auth/dang-ky";
					}
				}else {
					if(!khachHangDTO.getEmail().equals(KhachHangService.findByEmail(khachHangDTO.getEmail()))) {
						KhachHangService.register(khachHangDTO);
						khachHangDTO.setId(KhachHangService.findByEmail(khachHangDTO.getEmail()).getId());
						diaChiService.save(khachHangDTO);
						md.addAttribute("message","Đăng ký thành công !");
					}
				}
			return "/customer/auth/dang-ky";
			}
	}
	
	
}
