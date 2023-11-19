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

import com.fpoly.dto.KichCoDTO;
import com.fpoly.entity.KichCo;
import com.fpoly.service.KichCoService;

@Controller
@RequestMapping("admin/kich-co")
public class KichCoController {
	@Autowired
	private KichCoService kichCoService;

	@GetMapping("")
	public String chatLieu(Model model, HttpServletRequest request, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size, @RequestParam("messageSuccess") Optional<String> messageSuccess,
			@RequestParam("messageDanger") Optional<String> messageDanger) {
		String[] tenKichCoSearch = request.getParameterValues("tenKichCoSearch");
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
		Page<KichCo> resultPage = null;
		if (tenKichCoSearch == null) {
			List<KichCoDTO> dtos = new ArrayList<>();
			resultPage = kichCoService.selectAllKichCoExist(pageable);
			for (KichCo kichCo : resultPage.getContent()) {
				KichCoDTO dto = new KichCoDTO();
				dto.setId(kichCo.getId());
				dto.setTenKichCo(kichCo.getTenKichCo());
				dtos.add(dto);
			}
			model.addAttribute("kichCos", dtos);
		} else {
			if(!tenKichCoSearch[0].isEmpty()) {
				List<KichCoDTO> dtos = new ArrayList<>();
				resultPage = kichCoService.getKichCoExistByName(tenKichCoSearch[0], pageable);
				for (KichCo kichCo : resultPage.getContent()) {
					KichCoDTO dto = new KichCoDTO();
					dto.setId(kichCo.getId());
					dto.setTenKichCo(kichCo.getTenKichCo());
					dtos.add(dto);
				}
				model.addAttribute("kichCos", dtos);
			}else {
				List<KichCoDTO> dtos = new ArrayList<>();
				resultPage = kichCoService.selectAllKichCoExist(pageable);
				for (KichCo kichCo : resultPage.getContent()) {
					KichCoDTO dto = new KichCoDTO();
					dto.setId(kichCo.getId());
					dto.setTenKichCo(kichCo.getTenKichCo());
					dtos.add(dto);
				}
				model.addAttribute("kichCos", dtos);
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
		return "admin/kichCo/kichCoManage";
	}
	
	@PostMapping("createOrUpdate")
	public String createOrUpdate(RedirectAttributes redirect,@RequestParam("tenKichCoCreateOrUpdate") String tenKichCo,
			@RequestParam("kichCoIdCreateOrUpdate") String kichCoId) {
		final String redirectUrl = "redirect:/admin/kich-co";
		if(tenKichCo != null && kichCoId!= null) {
			if(tenKichCo.isEmpty()) {
				redirect.addFlashAttribute("messageDanger","Tên kích cỡ không được để trống");
				return redirectUrl;
			}
			Optional<KichCo> opt = kichCoService.findById(Long.parseLong(kichCoId));
			if(opt.isPresent()) {
				opt.get().setTenKichCo(tenKichCo);
				redirect.addFlashAttribute("messageSuccess","Cập nhật kích cỡ thành công");
				kichCoService.save(opt.get());
				return redirectUrl;
			}else {
				KichCo cl = new KichCo();
				cl.setTenKichCo(tenKichCo);
				redirect.addFlashAttribute("messageSuccess","Thêm mới kích cỡ thành công");
				kichCoService.save(cl);
				return redirectUrl;
			}
		}else {
			redirect.addFlashAttribute("messageDanger","Đã xảy ra lỗi khi cập nhật kích cỡ");
			return redirectUrl;
		}
	}
	
	@GetMapping("info/{id}")
	public String info(@PathVariable("id") Long id, Model model,RedirectAttributes redirect) {
		Optional<KichCo> opt = kichCoService.findById(id);
		if(opt.isPresent()) {
			model.addAttribute("kichCo", opt.get());
		}else {
			redirect.addFlashAttribute("messageDanger","Đã xảy ra lỗi khi tìm chi tiết kích cỡ");
			return "redirect:/admin/kich-co";
		}
		return "admin/kichCo/infoKichCo";
	}
	

	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") Long id, Model model,RedirectAttributes redirect) {
		Optional<KichCo> opt = kichCoService.findById(id);
		if(opt.isPresent()) {
			kichCoService.delete(opt.get());
			redirect.addFlashAttribute("messageSuccess","Xóa kích cỡ thành công");
			return "redirect:/admin/kich-co";
		}else {
			redirect.addFlashAttribute("messageDanger","Đã xảy ra lỗi khi xóa kích cỡ");
			return "redirect:/admin/kich-co";
		}
	}
}
