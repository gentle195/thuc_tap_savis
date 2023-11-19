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
import com.fpoly.dto.MauSacDTO;
import com.fpoly.entity.KieuDang;
import com.fpoly.entity.MauSac;
import com.fpoly.service.KieuDangService;
import com.fpoly.service.MauSacService;

@Controller
@RequestMapping("admin/mau-sac")
public class MauSacController {
	@Autowired
	private MauSacService mauSacService;

	@GetMapping("")
	public String chatLieu(Model model, HttpServletRequest request, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size, @RequestParam("messageSuccess") Optional<String> messageSuccess,
			@RequestParam("messageDanger") Optional<String> messageDanger) {
		String[] tenMauSacSearch = request.getParameterValues("tenMauSacSearch");
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
		Page<MauSac> resultPage = null;
		if (tenMauSacSearch == null) {
			List<MauSacDTO> dtos = new ArrayList<>();
			resultPage = mauSacService.selectAllMauSacExist(pageable);
			for (MauSac mauSac : resultPage.getContent()) {
				MauSacDTO dto = new MauSacDTO();
				dto.setId(mauSac.getId());
				dto.setTenMauSac(mauSac.getTenMauSac());	
				dto.setMaMauSac(mauSac.getMaMauSac());
				dtos.add(dto);
			}
			model.addAttribute("mauSacs", dtos);
		} else {
			if(!tenMauSacSearch[0].isEmpty()) {
				List<MauSacDTO> dtos = new ArrayList<>();
				resultPage = mauSacService.getMauSacExistByName(tenMauSacSearch[0],pageable);
				for (MauSac mauSac : resultPage.getContent()) {
					MauSacDTO dto = new MauSacDTO();
					dto.setId(mauSac.getId());
					dto.setTenMauSac(mauSac.getTenMauSac());
					dto.setMaMauSac(mauSac.getMaMauSac());
					dtos.add(dto);
				}
				model.addAttribute("mauSacs", dtos);
			}else {
				List<MauSacDTO> dtos = new ArrayList<>();
				resultPage = mauSacService.selectAllMauSacExist(pageable);
				for (MauSac mauSac : resultPage.getContent()) {
					MauSacDTO dto = new MauSacDTO();
					dto.setId(mauSac.getId());
					dto.setTenMauSac(mauSac.getTenMauSac());
					dto.setMaMauSac(mauSac.getMaMauSac());
					dtos.add(dto);
				}
				model.addAttribute("mauSacs", dtos);
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
		return "admin/mauSac/mauSacManage";
	}
	
	@PostMapping("createOrUpdate")
	public String createOrUpdate(RedirectAttributes redirect,
			@RequestParam("tenMauSacCreateOrUpdate") String tenMauSac,
			@RequestParam("maMauSacCreateOrUpdate") String maMauSac,
			@RequestParam("mauSacIdCreateOrUpdate") String mauSacId) {
		final String redirectUrl = "redirect:/admin/mau-sac";
		if(tenMauSac != null && mauSacId!= null) {
			if(tenMauSac.isEmpty()) {
				redirect.addFlashAttribute("messageDanger","Tên màu sắc không được để trống");
				return redirectUrl;
			}
			Optional<MauSac> opt = mauSacService.findById(Long.parseLong(mauSacId));
			if(opt.isPresent()) {
				opt.get().setTenMauSac(tenMauSac);
				opt.get().setMaMauSac(maMauSac);
				redirect.addFlashAttribute("messageSuccess","Cập nhật màu sắc thành công");
				mauSacService.save(opt.get());
				return redirectUrl;
			}else {
				MauSac cl = new MauSac();
				cl.setTenMauSac(tenMauSac);
				cl.setMaMauSac(maMauSac);
				redirect.addFlashAttribute("messageSuccess","Thêm mới màu sắc thành công");
				mauSacService.save(cl);
				return redirectUrl;
			}
		}else {
			redirect.addFlashAttribute("messageDanger","Đã xảy ra lỗi khi cập nhật màu sắc");
			return redirectUrl;
		}
	}
	
	@GetMapping("info/{id}")
	public String info(@PathVariable("id") Long id, Model model,RedirectAttributes redirect) {
		Optional<MauSac> opt = mauSacService.findById(id);
		if(opt.isPresent()) {
			model.addAttribute("mauSac", opt.get());
		}else {
			redirect.addFlashAttribute("messageDanger","Đã xảy ra lỗi khi tìm chi tiết màu sắc");
			return "redirect:/admin/mau-sac";
		}
		return "admin/mauSac/infoMauSac";
	}
	

	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") Long id, Model model,RedirectAttributes redirect) {
		Optional<MauSac> opt = mauSacService.findById(id);
		if(opt.isPresent()) {
			mauSacService.delete(opt.get());
			redirect.addFlashAttribute("messageSuccess","Xóa màu sắc thành công");
			return "redirect:/admin/mau-sac";
		}else {
			redirect.addFlashAttribute("messageDanger","Đã xảy ra lỗi khi xóa màu sắc");
			return "redirect:/admin/mau-sac";
		}
	}
}
