package com.fpoly.controller.customer;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fpoly.dto.KhachHangDTO;
import com.fpoly.service.KhachHangService;

@Controller
public class QuenMatKhauController {
	@Autowired
	private KhachHangService khachHangService ;
	
	@Autowired
	private HttpSession session ;
	
	@RequestMapping("/security/forgot-password")
	public String formForgotPassword1( Model model) {
		model.addAttribute("khachHangDTO",new KhachHangDTO());
		return "/customer/auth/quen-mat-khau" ;
	}
	@RequestMapping("/security/forgot-password-2")
	public String formForgotPassword2( Model model) {
		model.addAttribute("khachHangDTO",new KhachHangDTO());
		return "/customer/auth/quen-mat-khau-2" ;
	}
	
	
//	@RequestMapping("/security/forgot-password")
//	public String form( Model model) {
//		model.addAttribute("khachHangDTO",new KhachHangDTO());
//		return "/customer/auth/forgot-password" ;
//	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping("/security/forgot-password/enter-code")
	public String enterCode(
							@ModelAttribute("khachHangDTO") KhachHangDTO khachHangDTO ,
							Model model,RedirectAttributes redirectAtrributes) {
		String code = (String) session.getValue("code");
		String email = (String) session.getValue("email");
		if(code.equals(khachHangDTO.getCodeSend())) {
			if(!khachHangDTO.getMatKhau().equals(khachHangDTO.getMatKhauXacNhan())) {
				redirectAtrributes.addFlashAttribute("messageError","Mật khẩu không khớp !");
				return "redirect:/security/forgot-password-2" ;
			}
				khachHangService.updatePassword(email,khachHangDTO);
				redirectAtrributes.addFlashAttribute("message","Đổi mật khẩu thành công !");
				return "redirect:/security/forgot-password-2" ;
			
		}else if(!code.equals(khachHangDTO.getCodeSend())) {
			model.addAttribute("messageError","Không đúng mã !");
			return "/customer/auth/quen-mat-khau-2";
		}
		return "/customer/auth/quen-mat-khau-2" ;
	}
	
	@SuppressWarnings({"deprecation" })
	@RequestMapping("/security/forgot-password/sendCode")
	public String sendCode( Model model ) {
		String email = (String) session.getValue("email");
		String code = khachHangService.sendCode(email);
		session.removeAttribute("code");
		session.putValue("code",code);
		return "redirect:/security/forgot-password-2" ;
	}
	
}
