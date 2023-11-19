package com.fpoly.controller.admin.SanPham;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fpoly.dto.LoaiSanPhamDTO;
import com.fpoly.dto.PhongCachDTO;
import com.fpoly.entity.LoaiSanPham;
import com.fpoly.entity.PhongCach;
import com.fpoly.service.LoaiSanPhamService;
import com.fpoly.service.PhongCachService;

@Controller
@RequestMapping("admin/phong-cach")
public class PhongCachController {
	@Autowired
	private PhongCachService phongCachService;

	@GetMapping("")
	public String chatLieu(Model model, HttpServletRequest request, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size, @RequestParam("messageSuccess") Optional<String> messageSuccess,
			@RequestParam("messageDanger") Optional<String> messageDanger) {
		String[] tenPhongCachSearchs = request.getParameterValues("tenPhongCachSearch");
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
		Page<PhongCach> resultPage = null;
		if (tenPhongCachSearchs == null) {
			List<PhongCachDTO> dtos = new ArrayList<>();
			resultPage = phongCachService.selectAllPhongCachExist(pageable);
			for (PhongCach phongCach : resultPage.getContent()) {
				PhongCachDTO dto = new PhongCachDTO();
				dto.setId(phongCach.getId());
				dto.setTenPhongCach(phongCach.getTenPhongCach());
				dtos.add(dto);
			}
			model.addAttribute("phongCachs", dtos);
		} else {
			if(!tenPhongCachSearchs[0].isEmpty()) {
				List<PhongCachDTO> dtos = new ArrayList<>();
				resultPage = phongCachService.getPhongCachExistByName(tenPhongCachSearchs[0], pageable);
				for (PhongCach phongCach : resultPage.getContent()) {
					PhongCachDTO dto = new PhongCachDTO();
					dto.setId(phongCach.getId());
					dto.setTenPhongCach(phongCach.getTenPhongCach());
					dtos.add(dto);
				}
				model.addAttribute("phongCachs", dtos);
			}else {
				List<PhongCachDTO> dtos = new ArrayList<>();
				resultPage = phongCachService.selectAllPhongCachExist(pageable);
				for (PhongCach phongCach : resultPage.getContent()) {
					PhongCachDTO dto = new PhongCachDTO();
					dto.setId(phongCach.getId());
					dto.setTenPhongCach(phongCach.getTenPhongCach());
					dtos.add(dto);
				}
				model.addAttribute("phongCachs", dtos);
			}
		}
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
		if (messageSuccess.isPresent()) {
			model.addAttribute("messageSuccess", messageSuccess.get());
		}
		if (messageSuccess.isPresent()) {
			model.addAttribute("messageDanger", messageDanger.get());
		}
		model.addAttribute("resultPage", resultPage);
		return "admin/phongCach/phongCachManage";
	}
	
	@PostMapping("createOrUpdate")
	public String createOrUpdate(RedirectAttributes redirect,
			@RequestParam("tenPhongCachCreateOrUpdate") String tenPhongCach,
			@RequestParam("phongCachIdCreateOrUpdate") String phongCachId) {
		final String redirectUrl = "redirect:/admin/phong-cach";
		if(tenPhongCach != null && phongCachId!= null) {
			if(phongCachId.isEmpty()) {
				redirect.addFlashAttribute("messageDanger","Tên phong cách không được để trống");
				return redirectUrl;
			}
			Optional<PhongCach> opt = phongCachService.findById(Long.parseLong(phongCachId));
			if(opt.isPresent()) {
				opt.get().setTenPhongCach(tenPhongCach);
				redirect.addFlashAttribute("messageSuccess","Cập nhật phong cách thành công");
				phongCachService.save(opt.get());
				return redirectUrl;
			}else {
				PhongCach cl = new PhongCach();
				cl.setTenPhongCach(tenPhongCach);
				redirect.addFlashAttribute("messageSuccess","Thêm mới phong cách thành công");
				phongCachService.save(cl);
				return redirectUrl;
			}
		}else {
			redirect.addFlashAttribute("messageDanger","Đã xảy ra lỗi khi cập nhật phong cách");
			return redirectUrl;
		}
	}
	
	@GetMapping("info/{id}")
	public String info(@PathVariable("id") Long id, Model model,RedirectAttributes redirect) {
		Optional<PhongCach> opt = phongCachService.findById(id);
		if(opt.isPresent()) {
			model.addAttribute("phongCach", opt.get());
		}else {
			redirect.addFlashAttribute("messageDanger","Đã xảy ra lỗi khi tìm chi tiết phong cách");
			return "redirect:/admin/phong-cach";
		}
		return "admin/phongCach/infoPhongCach";
	}
	

	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") Long id, Model model,RedirectAttributes redirect) {
		Optional<PhongCach> opt = phongCachService.findById(id);
		if(opt.isPresent()) {
			phongCachService.delete(opt.get());
			redirect.addFlashAttribute("messageSuccess","Xóa phong cách thành công");
			return "redirect:/admin/phong-cach";
		}else {
			redirect.addFlashAttribute("messageDanger","Đã xảy ra lỗi khi xóa phong cách");
			return "redirect:/admin/phong-cach";
		}
	}
}
