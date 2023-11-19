package com.fpoly.controller.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fpoly.service.KhachHangService;

@CrossOrigin("*")
@Controller
public class DangNhapController {
	@Autowired
	private KhachHangService khachHangService ;
	
	@Autowired
	private HttpServletRequest request ;
	
	@Autowired
	private HttpSession session ;
	
	
    @RequestMapping("/security/login/form")
	public String loginForm(Model model) {
    	model.addAttribute("alert",request.getParameter("alert"));
		model.addAttribute("message","Vui lòng đăng nhâp !");
		return "/customer/auth/login2";
	}
    @RequestMapping("/security/login/success")
	public String success(Model model ) {
    	model.addAttribute("alert",request.getParameter("alert"));
		model.addAttribute("message","Đăng nhập thành công!");
		return "/customer/auth/login2";
	}
	
	@RequestMapping("/security/login/error")
	public String loginError(Model model) {
		model.addAttribute("alert",request.getParameter("alert"));
		model.addAttribute("message","Sai thông tin đăng nhập !");
		return "/customer/auth/login2";
	}
	@RequestMapping("/security/unauthoried")
	public String unauthoried(Model model) {
		model.addAttribute("alert",request.getParameter("alert"));
		model.addAttribute("message","Bạn không có quyền truy xuất !");
		return "/customer/auth/login2";
	}
	
	@RequestMapping("/security/logoff/success")
	public String logoffSuccess(Model model) {
		model.addAttribute("alert",request.getParameter("alert"));
		model.addAttribute("message","Bạn đã đăng xuất");
		return "/customer/auth/login2";
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping("/security/forgot-password/code")
	public String getEmail(@RequestParam("email") String email , Model model ,RedirectAttributes redirectAtrributes) {
		session.putValue("email",email);
		if(khachHangService.findByEmail(email) == null ) {
			model.addAttribute("messageError","Email chưa được đăng ký");
			return "/customer/auth/quen-mat-khau";
		}else {
			String code = khachHangService.sendCode(email);
			session.putValue("code",code);
			return "redirect:/security/forgot-password-2";
		}
	}
}
