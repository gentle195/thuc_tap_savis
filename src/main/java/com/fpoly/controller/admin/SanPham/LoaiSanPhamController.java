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

import com.fpoly.dto.ChatLieuDTO;
import com.fpoly.dto.LoaiSanPhamDTO;
import com.fpoly.entity.ChatLieu;
import com.fpoly.entity.LoaiSanPham;
import com.fpoly.service.ChatLieuService;
import com.fpoly.service.LoaiSanPhamService;
import com.fpoly.service.SanPhamService;


@Controller
@RequestMapping("admin/loai-san-pham")
public class LoaiSanPhamController {
	@Autowired
	private LoaiSanPhamService loaiSanPhamService;

	@GetMapping("")
	public String chatLieu(Model model, HttpServletRequest request, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size, @RequestParam("messageSuccess") Optional<String> messageSuccess,
			@RequestParam("messageDanger") Optional<String> messageDanger) {
		String[] tenLoaiSanPhamSearchs = request.getParameterValues("tenLoaiSanPhamSearch");
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
		Page<LoaiSanPham> resultPage = null;
		if (tenLoaiSanPhamSearchs == null) {
			List<LoaiSanPhamDTO> dtos = new ArrayList<>();
			resultPage = loaiSanPhamService.selectAllLoaiSanPhamExist(pageable);
			for (LoaiSanPham loaiSanPham : resultPage.getContent()) {
				LoaiSanPhamDTO dto = new LoaiSanPhamDTO();
				dto.setId(loaiSanPham.getId());
				dto.setTenLoaiSanPham(loaiSanPham.getTenLoaiSanPham());
				dtos.add(dto);
			}
			model.addAttribute("loaiSanPhams", dtos);
		} else {
			if(!tenLoaiSanPhamSearchs[0].isEmpty()) {
				List<LoaiSanPhamDTO> dtos = new ArrayList<>();
				resultPage = loaiSanPhamService.getLoaiSanPhamExistByName(tenLoaiSanPhamSearchs[0], pageable);
				for (LoaiSanPham loaiSanPham : resultPage.getContent()) {
					LoaiSanPhamDTO dto = new LoaiSanPhamDTO();
					dto.setId(loaiSanPham.getId());
					dto.setTenLoaiSanPham(loaiSanPham.getTenLoaiSanPham());
					dtos.add(dto);
				}
				model.addAttribute("loaiSanPhams", dtos);
			}else {
				List<LoaiSanPhamDTO> dtos = new ArrayList<>();
				resultPage = loaiSanPhamService.selectAllLoaiSanPhamExist(pageable);
				for (LoaiSanPham loaiSanPham : resultPage.getContent()) {
					LoaiSanPhamDTO dto = new LoaiSanPhamDTO();
					dto.setId(loaiSanPham.getId());
					dto.setTenLoaiSanPham(loaiSanPham.getTenLoaiSanPham());
					dtos.add(dto);
				}
				model.addAttribute("loaiSanPhams", dtos);
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
		return "admin/loaiSanPham/loaiSanPhamManage";
	}
	
	@PostMapping("createOrUpdate")
	public String createOrUpdate(RedirectAttributes redirect,
			@RequestParam("tenLoaiSanPhamCreateOrUpdate") String tenLoaiSanPham,
			@RequestParam("loaiSanPhamIdCreateOrUpdate") String loaiSanPhamId) {
		final String redirectUrl = "redirect:/admin/loai-san-pham";
		if(tenLoaiSanPham != null && loaiSanPhamId!= null) {
			if(loaiSanPhamId.isEmpty()) {
				redirect.addFlashAttribute("messageDanger","Tên loại sản phẩm không được để trống");
				return redirectUrl;
			}
			Optional<LoaiSanPham> opt = loaiSanPhamService.findById(Long.parseLong(loaiSanPhamId));
			if(opt.isPresent()) {
				opt.get().setTenLoaiSanPham(tenLoaiSanPham);
				redirect.addFlashAttribute("messageSuccess","Cập nhật loại sản phẩm thành công");
				loaiSanPhamService.save(opt.get());
				return redirectUrl;
			}else {
				LoaiSanPham cl = new LoaiSanPham();
				cl.setTenLoaiSanPham(tenLoaiSanPham);
				redirect.addFlashAttribute("messageSuccess","Thêm mới loại sản phẩm thành công");
				loaiSanPhamService.save(cl);
				return redirectUrl;
			}
		}else {
			redirect.addFlashAttribute("messageDanger","Đã xảy ra lỗi khi cập nhật loại sản phẩm");
			return redirectUrl;
		}
	}
	
	@GetMapping("info/{id}")
	public String info(@PathVariable("id") Long id, Model model,RedirectAttributes redirect) {
		Optional<LoaiSanPham> opt = loaiSanPhamService.findById(id);
		if(opt.isPresent()) {
			model.addAttribute("loaiSanPham", opt.get());
		}else {
			redirect.addFlashAttribute("messageDanger","Đã xảy ra lỗi khi tìm chi tiết loại sản phẩm");
			return "redirect:/admin/loai-san-pham";
		}
		return "admin/loaiSanPham/infoLoaiSanPham";
	}
	

	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") Long id, Model model,RedirectAttributes redirect) {
		Optional<LoaiSanPham> opt = loaiSanPhamService.findById(id);
		if(opt.isPresent()) {
			loaiSanPhamService.delete(opt.get());
			redirect.addFlashAttribute("messageSuccess","Xóa loại sản phẩm thành công");
			return "redirect:/admin/loai-san-pham";
		}else {
			redirect.addFlashAttribute("messageDanger","Đã xảy ra lỗi khi xóa loại sản phẩm");
			return "redirect:/admin/loai-san-pham";
		}
	}
}
