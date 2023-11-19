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

import com.fpoly.dto.KieuDangDTO;
import com.fpoly.entity.KieuDang;
import com.fpoly.service.KieuDangService;


@Controller
@RequestMapping("admin/kieu-dang")
public class KieuDangController {
	@Autowired
	private KieuDangService kieuDangService;

	@GetMapping("")
	public String chatLieu(Model model, HttpServletRequest request, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size, @RequestParam("messageSuccess") Optional<String> messageSuccess,
			@RequestParam("messageDanger") Optional<String> messageDanger) {
		String[] tenKieuDangSearch = request.getParameterValues("tenKieuDangSearch");
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
		Page<KieuDang> resultPage = null;
		if (tenKieuDangSearch == null) {
			List<KieuDangDTO> dtos = new ArrayList<>();
			resultPage = kieuDangService.selectAllKieuDangExist(pageable);
			for (KieuDang kieuDang : resultPage.getContent()) {
				KieuDangDTO dto = new KieuDangDTO();
				dto.setId(kieuDang.getId());
				dto.setTenKieuDang(kieuDang.getTenKieuDang());
				dtos.add(dto);
			}
			model.addAttribute("kieuDangs", dtos);
		} else {
			if(!tenKieuDangSearch[0].isEmpty()) {
				List<KieuDangDTO> dtos = new ArrayList<>();
				resultPage = kieuDangService.getKieuDangExistByName(tenKieuDangSearch[0], pageable);
				for (KieuDang kieuDang : resultPage.getContent()) {
					KieuDangDTO dto = new KieuDangDTO();
					dto.setId(kieuDang.getId());
					dto.setTenKieuDang(kieuDang.getTenKieuDang());
					dtos.add(dto);
				}
				model.addAttribute("kieuDangs", dtos);
			}else {
				List<KieuDangDTO> dtos = new ArrayList<>();
				resultPage = kieuDangService.selectAllKieuDangExist(pageable);
				for (KieuDang kieuDang : resultPage.getContent()) {
					KieuDangDTO dto = new KieuDangDTO();
					dto.setId(kieuDang.getId());
					dto.setTenKieuDang(kieuDang.getTenKieuDang());
					dtos.add(dto);
				}
				model.addAttribute("kieuDangs", dtos);
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
		return "admin/kieuDang/kieuDangManage";
	}
	
	@PostMapping("createOrUpdate")
	public String createOrUpdate(RedirectAttributes redirect,@RequestParam("tenKieuDangCreateOrUpdate") String tenKieuDang,
			@RequestParam("kieuDangIdCreateOrUpdate") String kieuDangId) {
		final String redirectUrl = "redirect:/admin/kieu-dang";
		if(tenKieuDang != null && kieuDangId!= null) {
			if(tenKieuDang.isEmpty()) {
				redirect.addFlashAttribute("messageDanger","Tên kiểu dáng không được để trống");
				return redirectUrl;
			}
			Optional<KieuDang> opt = kieuDangService.findById(Long.parseLong(kieuDangId));
			if(opt.isPresent()) {
				opt.get().setTenKieuDang(tenKieuDang);
				redirect.addFlashAttribute("messageSuccess","Cập nhật kiểu dáng thành công");
				kieuDangService.save(opt.get());
				return redirectUrl;
			}else {
				KieuDang cl = new KieuDang();
				cl.setTenKieuDang(tenKieuDang);
				redirect.addFlashAttribute("messageSuccess","Thêm mới kiểu dáng thành công");
				kieuDangService.save(cl);
				return redirectUrl;
			}
		}else {
			redirect.addFlashAttribute("messageDanger","Đã xảy ra lỗi khi cập nhật kiểu dáng");
			return redirectUrl;
		}
	}
	
	@GetMapping("info/{id}")
	public String info(@PathVariable("id") Long id, Model model,RedirectAttributes redirect) {
		Optional<KieuDang> opt = kieuDangService.findById(id);
		if(opt.isPresent()) {
			model.addAttribute("kieuDang", opt.get());
		}else {
			redirect.addFlashAttribute("messageDanger","Đã xảy ra lỗi khi tìm chi tiết kiểu dáng");
			return "redirect:/admin/kieu-dang";
		}
		return "admin/kieuDang/infoKieuDang";
	}
	

	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") Long id, Model model,RedirectAttributes redirect) {
		Optional<KieuDang> opt = kieuDangService.findById(id);
		if(opt.isPresent()) {
			kieuDangService.delete(opt.get());
			redirect.addFlashAttribute("messageSuccess","Xóa kiểu dáng thành công");
			return "redirect:/admin/kieu-dang";
		}else {
			redirect.addFlashAttribute("messageDanger","Đã xảy ra lỗi khi xóa kiểu dáng");
			return "redirect:/admin/kieu-dang";
		}
	}
}
