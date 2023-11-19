package com.fpoly.controller.admin.SanPham;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.fpoly.constant.OptionContants;
import com.fpoly.constant.pageContants;
import com.fpoly.dto.ChatLieuDTO;
import com.fpoly.dto.HinhAnhDTO;
import com.fpoly.dto.KichCoDTO;
import com.fpoly.dto.KieuDangDTO;
import com.fpoly.dto.LoaiSanPhamDTO;
import com.fpoly.dto.MauSacDTO;
import com.fpoly.dto.PhongCachDTO;
import com.fpoly.dto.SanPhamChiTietDTO;
import com.fpoly.dto.SanPhamDTO;
import com.fpoly.dto.composite.HinhAnhMauSacDTO;
import com.fpoly.dto.composite.SanPhamManageDTO;
import com.fpoly.dto.composite.SanPhamProductManageDTO;
import com.fpoly.dto.search.SPAndSPCTSearchDto;
import com.fpoly.entity.ChatLieu;
import com.fpoly.entity.HinhAnh;
import com.fpoly.entity.KichCo;
import com.fpoly.entity.KieuDang;
import com.fpoly.entity.LoaiSanPham;
import com.fpoly.entity.MauSac;
import com.fpoly.entity.PhongCach;
import com.fpoly.entity.SanPham;
import com.fpoly.entity.SanPhamChiTiet;
import com.fpoly.service.ChatLieuService;
import com.fpoly.service.HinhAnhService;
import com.fpoly.service.KichCoService;
import com.fpoly.service.KieuDangService;
import com.fpoly.service.LoaiSanPhamService;
import com.fpoly.service.MauSacService;
import com.fpoly.service.PhongCachService;
import com.fpoly.service.SanPhamChiTietService;
import com.fpoly.service.SanPhamService;
import com.fpoly.service.StorageService;

@Controller
@RequestMapping("admin/product")
public class SanPhamChiTietController {
	@Autowired
	private SanPhamChiTietService sanPhamChiTietService;

	@Autowired
	private SanPhamService sanPhamService;

	@Autowired
	private MauSacService mauSacService;

	@Autowired
	private ChatLieuService chatLieuService;

	@Autowired
	private KichCoService kichCoService;

	@Autowired
	private LoaiSanPhamService loaiSanPhamService;

	@Autowired
	private PhongCachService phongCachService;

	@Autowired
	private KieuDangService kieuDangService;

	@Autowired
	private StorageService storageService;

	@Autowired
	private HinhAnhService hinhAnhService;

//	private ProductDetailsWithColorSizeRepository productDetailsWithColorSizeRepository;

	@GetMapping("/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Resource file = storageService.loadAsResource(filename);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@ModelAttribute("lstMauSac")
	public List<MauSacDTO> getLstMauSac() {
		return mauSacService.selectAllMauSacExist().stream().map(item -> {
			MauSacDTO dto = new MauSacDTO();
			BeanUtils.copyProperties(item, dto);
			return dto;
		}).collect(Collectors.toList());
	}

	@ModelAttribute("lstKieuDang")
	public List<KieuDangDTO> getLstKieuDang() {
		return kieuDangService.selectAllKieuDangExist().stream().map(item -> {
			KieuDangDTO dto = new KieuDangDTO();
			BeanUtils.copyProperties(item, dto);
			return dto;
		}).collect(Collectors.toList());
	}

	@ModelAttribute("lstChatLieu")
	public List<ChatLieuDTO> getLstChatLieu() {
		return chatLieuService.selectAllChatLieuExist().stream().map(item -> {
			ChatLieuDTO dto = new ChatLieuDTO();
			BeanUtils.copyProperties(item, dto);
			return dto;
		}).collect(Collectors.toList());
	}

	@ModelAttribute("lstKichCo")
	public List<KichCoDTO> getLstKichCo() {
		return kichCoService.selectAllKichCoExist().stream().map(item -> {
			KichCoDTO dto = new KichCoDTO();
			BeanUtils.copyProperties(item, dto);
			return dto;
		}).collect(Collectors.toList());
	}

	@ModelAttribute("lstLoaiSanPham")
	public List<LoaiSanPhamDTO> getLstLoaiHang() {
		return loaiSanPhamService.selectAllLoaiHangExist().stream().map(item -> {
			LoaiSanPhamDTO dto = new LoaiSanPhamDTO();
			BeanUtils.copyProperties(item, dto);
			return dto;
		}).collect(Collectors.toList());
	}

	@ModelAttribute("lstPhongCach")
	public List<PhongCachDTO> getLstPhongCach() {
		return phongCachService.selectAllPhongCachExist().stream().map(item -> {
			PhongCachDTO dto = new PhongCachDTO();
			BeanUtils.copyProperties(item, dto);
			return dto;
		}).collect(Collectors.toList());
	}

	@GetMapping("")
	public String search(ModelMap model, @ModelAttribute(name = "dataSearch") SPAndSPCTSearchDto dataSearch,
			@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
			@RequestParam("messageSuccess") Optional<String> messageSuccess,
			@RequestParam("messageDanger") Optional<String> messageDanger) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);

		Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
		Page<SanPham> resultPage = null;
		Optional<SPAndSPCTSearchDto> optDataSearch = Optional.of(dataSearch);
		if (optDataSearch.isPresent()) {
			resultPage = sanPhamService.searchProductExist(dataSearch, pageable);
			model.addAttribute("dataSearch", dataSearch);
			List<SanPhamProductManageDTO> lstDto = new ArrayList<>();
			for (SanPham sp : resultPage.getContent()) {
				SanPhamProductManageDTO dto = new SanPhamProductManageDTO();
				int tongSoLuong = sanPhamChiTietService.getSumSoLuongBySanPhamId(sp.getId());
				dto.setTongSoLuong(tongSoLuong);
				dto.setSanPham(sp);
				List<HinhAnh> lstAnhChinh = hinhAnhService.getHinhAnhChinhBySanPhamId(sp.getId());
				List<String> anhChinhs = lstAnhChinh.stream().map(HinhAnh::getTenAnh).collect(Collectors.toList());
				dto.setAnhChinhs(anhChinhs);
				lstDto.add(dto);
			}
			model.addAttribute("sanPhams", lstDto);
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
//			if(!messageSuccess.isEmpty()) {
			model.addAttribute("messageSuccess", messageSuccess.get());
//			}
		}
		if (messageSuccess.isPresent()) {
//			if(!messageDanger.isEmpty()) {
			model.addAttribute("messageDanger", messageDanger.get());
//			}
		}
		model.addAttribute("sanPhamPage", resultPage);
		return "admin/product/productManage";
	}

	@GetMapping("info/{id}")
	public String infoProductDetai(ModelMap model, @PathVariable("id") Long id, RedirectAttributes redirect) {
		SanPhamDTO dto = new SanPhamDTO();
		Optional<SanPham> opt = sanPhamService.findById(id);
		List<SanPhamChiTiet> lstSp = sanPhamChiTietService.getLstSanPhamChiTietBySanPhamId(id);
		if(opt.isPresent()) {
			dto.setSanPhamChiTiets(lstSp);
			dto.setGia(opt.get().getGia());
			dto.setTenChatLieu(opt.get().getChatLieu().getTenChatLieu());
			dto.setTenKieuDang(opt.get().getKieuDang().getTenKieuDang());
			dto.setTenLoaiSanPham(opt.get().getLoaiSanPham().getTenLoaiSanPham());
			dto.setTenPhongCach(opt.get().getPhongCach().getTenPhongCach());
			dto.setTenSanPham(opt.get().getTenSanPham());
			dto.setMoTa(opt.get().getMoTa());
			List<HinhAnh> ha = hinhAnhService.getHinhAnhBySanPhamId(id);
			if (opt.isPresent()) {
				model.addAttribute("sanPham", dto);
				model.addAttribute("imgs", ha);
			}
			return "admin/product/infoProduct";
		}else {
			redirect.addFlashAttribute("messageDanger","Sản phẩm không tồn tại");
			return "redirect:/admin/product";
		}
	}

	@GetMapping("add")
	public String addProductDetail(ModelMap model,
			@ModelAttribute("sanPhamManageDTO") Optional<SanPhamManageDTO> sanPhamManageDTO) {
		SanPhamManageDTO sanPhamManageDTONew = new SanPhamManageDTO();
		if (sanPhamManageDTO.isPresent()) {
			sanPhamManageDTONew.setChatLieuId(sanPhamManageDTO.get().getChatLieuId());
			sanPhamManageDTONew.setGia(sanPhamManageDTO.get().getGia());
			sanPhamManageDTONew.setKichCoIds(sanPhamManageDTO.get().getKichCoIds());
			sanPhamManageDTONew.setKieuDangId(sanPhamManageDTO.get().getKieuDangId());
			sanPhamManageDTONew.setLoaiSanPhamId(sanPhamManageDTO.get().getLoaiSanPhamId());
			sanPhamManageDTONew.setMauSacIds(sanPhamManageDTO.get().getMauSacIds());
			sanPhamManageDTONew.setMoTa(sanPhamManageDTO.get().getMoTa());
			sanPhamManageDTONew.setPhongCachId(sanPhamManageDTO.get().getPhongCachId());
			sanPhamManageDTONew.setSanPhamId(sanPhamManageDTO.get().getSanPhamId());
			sanPhamManageDTONew.setSoLuong(sanPhamManageDTO.get().getSoLuong());
			sanPhamManageDTONew.setTenSanPham(sanPhamManageDTO.get().getTenSanPham());
		}
		model.addAttribute("sanPhamManageDTO", sanPhamManageDTONew);
		return "admin/product/addProduct";
	}

	@PostMapping("generateProductDetails")
	public String generateProductDetails(ModelMap model,
			@Valid @ModelAttribute("sanPhamManageDTO") SanPhamManageDTO data, BindingResult result) {
		if (result.hasErrors()) {
			model.addAttribute("sanPhamManageDTO", data);
			return "admin/product/addProduct";
		} else {
			SanPham sanPham = new SanPham();
			sanPham.setDaXoa(false);
			sanPham.setGia(data.getGia());
			sanPham.setTenSanPham(data.getTenSanPham());
			sanPham.setMoTa(data.getMoTa());

			ChatLieu chatLieu = new ChatLieu();
			chatLieu.setId(data.getChatLieuId());
			sanPham.setChatLieu(chatLieu);

			LoaiSanPham loaiSanPham = new LoaiSanPham();
			loaiSanPham.setId(data.getLoaiSanPhamId());
			sanPham.setLoaiSanPham(loaiSanPham);

			PhongCach phongCach = new PhongCach();
			phongCach.setId(data.getPhongCachId());
			sanPham.setPhongCach(phongCach);

			KieuDang kieuDang = new KieuDang();
			kieuDang.setId(data.getKieuDangId());
			sanPham.setKieuDang(kieuDang);

			sanPhamService.save(sanPham);

			for (Long kichCoId : data.getKichCoIds()) {
				for (Long mauSacId : data.getMauSacIds()) {
					SanPhamChiTiet spct = new SanPhamChiTiet();
					spct.setCoHienThi(true);
					spct.setDaXoa(false);
					spct.setSanPham(sanPham);
					int soLuong = data.getSoLuong();
					spct.setSoLuong(soLuong);

					KichCo kichCo = new KichCo();
					kichCo.setId(kichCoId);
					spct.setKichCo(kichCo);

					MauSac mauSac = new MauSac();
					mauSac.setId(mauSacId);
					spct.setMauSac(mauSac);

					sanPhamChiTietService.save(spct);
				}
			}
			List<SanPhamChiTiet> dataGen = sanPhamChiTietService.getLstSanPhamChiTietBySanPhamId(sanPham.getId());
			dataGen.forEach(i -> {
				Optional<MauSac> optMS = mauSacService.findById(i.getMauSac().getId());
				Optional<KichCo> optKC = kichCoService.findById(i.getKichCo().getId());
				i.setMauSac(optMS.get());
				i.setKichCo(optKC.get());
			});
			List<MauSac> lstMauSacAddImg = mauSacService.getAllMauSacExistBySPId(sanPham.getId());
			//button - mau sac add img
			HashMap<Long, String> lstMauSacAddImgHM = new HashMap<Long, String>();
			for (MauSac mauSac : lstMauSacAddImg) {
				lstMauSacAddImgHM.put(mauSac.getId(), mauSac.getTenMauSac());
			}
			data.setLstMauSacAddImg(lstMauSacAddImgHM);
			
			data.setSanPhamId(sanPham.getId());
			data.setIsGenaratedData(true);
			model.addAttribute("dataGen", dataGen);
			model.addAttribute("messageSuccess", "Tạo sản phẩm và chi tiết sản phẩm thành công");
			model.addAttribute("sanPhamManageDTO", data);
			return "/admin/product/addProduct";
		}
	}

	@GetMapping("changeIsShowFormAddProduct/{id}")
	@ResponseBody
	public ResponseEntity<?> changeIsShowFormAddProduct(ModelMap model, @PathVariable("id") Long id) {
		Optional<SanPhamChiTiet> opt = sanPhamChiTietService.findById(id);
		if (opt.isPresent()) {
			opt.get().setCoHienThi(!opt.get().getCoHienThi());
			sanPhamChiTietService.save(opt.get());
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("edit/{id}")
	public String edit(ModelMap model, @PathVariable("id") Long id,
			@RequestParam("messageDanger") Optional<String> messageDanger,
			@RequestParam("messageSuccess") Optional<String> messageSuccess) {
		Optional<SanPham> optSP = sanPhamService.findById(id);
		if (optSP.isPresent()) {
			List<SanPhamChiTiet> dataGen = sanPhamChiTietService.getLstSanPhamChiTietBySanPhamId(optSP.get().getId());
			
			SanPhamManageDTO dto = new SanPhamManageDTO();
			//button - mau sac add img
			List<MauSac> lstMauSacAddImg = mauSacService.getAllMauSacExistBySPId(id);
			HashMap<Long, String> lstMauSacAddImgHM = new HashMap<Long, String>();
			for (MauSac mauSac : lstMauSacAddImg) {
				lstMauSacAddImgHM.put(mauSac.getId(), mauSac.getTenMauSac());
			}
			dto.setLstMauSacAddImg(lstMauSacAddImgHM);
			
			BeanUtils.copyProperties(optSP.get(), dto);
			dto.setSanPhamId(optSP.get().getId());
			dto.setIsEdit(true);
			dto.setLoaiSanPhamId(optSP.get().getLoaiSanPham().getId());
			dto.setKieuDangId(optSP.get().getKieuDang().getId());
			dto.setChatLieuId(optSP.get().getChatLieu().getId());
			dto.setPhongCachId(optSP.get().getPhongCach().getId());
			List<Long> lstKC = dataGen.stream().map(i -> i.getKichCo().getId()).collect(Collectors.toList());
			List<Long> lstMS = dataGen.stream().map(i -> i.getMauSac().getId()).collect(Collectors.toList());
			dto.setKichCoIds(lstKC);
			dto.setMauSacIds(lstMS);

			List<HinhAnh> lstHinhAnh = hinhAnhService.getLstHinhAnhMauSacBySanPhamId(id);
			List<List<HinhAnhDTO>> lstHinhAnhDTOs = new ArrayList<>();
			int i = 0;
			int j = 0;
			int countLstHinhAnh = 0;
			do {
				List<HinhAnhDTO> HinhAnhDTOs = new ArrayList<>();
				for (j = i; j < lstHinhAnh.size(); j++) {
					if (lstHinhAnh.get(j).getMauSac().getId().equals(lstHinhAnh.get(i).getMauSac().getId())) {
						HinhAnhDTO haDto = new HinhAnhDTO();
						BeanUtils.copyProperties(lstHinhAnh.get(j), haDto);
						haDto.setCoHienThi(lstHinhAnh.get(j).getCoHienThi());
						haDto.setLaAnhChinh(lstHinhAnh.get(j).getLaAnhChinh());
						haDto.setHinhAnhid(lstHinhAnh.get(j).getId());
						HinhAnhDTOs.add(haDto);
					} else {
						i = j;
						break;
					}
				}
				countLstHinhAnh++;

				lstHinhAnhDTOs.add(HinhAnhDTOs);
				if (j == lstHinhAnh.size())
					break;
			} while (j < lstHinhAnh.size());
			int m = 0;
			List<HinhAnhMauSacDTO> lstHinhAnhMauSacDTO = new ArrayList<>();
			List<Long> lstHinhAnhDistinct = hinhAnhService.getDistinctMauSacInHinhAnhBySanPhamId(id);
			if (lstHinhAnhDistinct.size() != 0) {
				for (Long mauSacId : lstHinhAnhDistinct) {
					HinhAnhMauSacDTO hinhAnhMauSacDTO = new HinhAnhMauSacDTO();
					Optional<MauSac> optMS = mauSacService.findById(mauSacId);
					if (optMS.isPresent()) {
						hinhAnhMauSacDTO.setTenMauSacAddImg(optMS.get().getTenMauSac());
						hinhAnhMauSacDTO.setMauSacAddImagesId(mauSacId);
					}
					if (m < countLstHinhAnh) {
						List<HinhAnhDTO> lstHinhAnhDTO = new ArrayList<HinhAnhDTO>();
						for (HinhAnhDTO item : lstHinhAnhDTOs.get(m)) {
							lstHinhAnhDTO.add(item);
						}
						hinhAnhMauSacDTO.setHinhAnhDTOs(lstHinhAnhDTO);
						m++;
					}
					lstHinhAnhMauSacDTO.add(hinhAnhMauSacDTO);
				}
				dto.setLstHinhAnhMauSacDTO(lstHinhAnhMauSacDTO);
			}
			dto.setIsGenaratedData(true);
			dto.setIsCreatedValueImg(true);
			dto.setIsAddProductSuccess(true);
			int count = hinhAnhService.getCountHinhAnhBySanPhamId(dto.getSanPhamId());
			if(count > 0) {
				dto.setIsHaveImg(true);
			}else dto.setIsHaveImg(false);
			model.addAttribute("sanPhamManageDTO", dto);
			model.addAttribute("dataGen", dataGen);
		}
		if (messageDanger.isPresent()) {
			model.addAttribute("messageDanger", messageDanger.get());
		}

		if (messageSuccess.isPresent()) {
			model.addAttribute("messageSuccess", messageSuccess.get());
		}
		return "admin/product/addProduct";
	}

	@PostMapping("deleteAllByIdsProductManage")
	public String deleteAllByIdProductManage(ModelMap model,
			@ModelAttribute("dataSearch") SPAndSPCTSearchDto dataSearch, HttpServletRequest request,
			@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
			final RedirectAttributes redirectAttributes) throws IOException {
		String[] ids = request.getParameterValues("productIds");
		boolean isSuccess = false;
		if (ids != null) {
			for (String item : ids) {
				Optional<SanPham> opt = sanPhamService.findById(Long.parseLong(item));
				if (opt.isPresent()) {
					List<HinhAnh> lstHinhAnh = hinhAnhService.getHinhAnhBySanPhamId(Long.parseLong(item));
					for (HinhAnh ha : lstHinhAnh) {
						if (!ha.getTenAnh().isEmpty()) {
							storageService.delete(ha.getTenAnh());
							hinhAnhService.delete(ha);
						} else {
							redirectAttributes.addFlashAttribute("messageDanger",
									"Tên hình ảnh: '" + ha.getTenAnh() + "' không tồn tại");
						}
					}
					sanPhamService.delete(opt.get());
					for (SanPhamChiTiet spct : opt.get().getSanPhamChiTiets()) {
						sanPhamChiTietService.delete(spct);
					}
					isSuccess = true;
				} else {
					redirectAttributes.addFlashAttribute("messageDanger",
							"Xóa sản phẩm có tên: " + opt.get().getTenSanPham() + " thất bại");
					isSuccess = false;
				}
			}
		} else {
			isSuccess = false;
			redirectAttributes.addFlashAttribute("messageDanger", "Sản phẩm không tồn tại");
		}
		if (isSuccess == true) {
			redirectAttributes.addFlashAttribute("messageSuccess", "Xóa sản phẩm thành công");
			redirectAttributes.addFlashAttribute("page", page);
			redirectAttributes.addFlashAttribute("size", size);
			redirectAttributes.addFlashAttribute("dataSearch", dataSearch);
			return "redirect:/admin/product";
		} else {
			redirectAttributes.addFlashAttribute("page", page);
			redirectAttributes.addFlashAttribute("size", size);
			redirectAttributes.addFlashAttribute("dataSearch", dataSearch);
			return "redirect:/admin/product";
		}
	}

	@PostMapping("updateQuantityProductDetail")
	public String updateQuantityProductDetail(ModelMap model, @ModelAttribute("sanPhamManageDTO") SanPhamManageDTO data,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirect) throws IOException {		
		if(data.getGia() == null) {
			model.addAttribute("messageDanger", "bạn không được để giá sản phẩm trống");
			List<SanPhamChiTiet> dataGen = sanPhamChiTietService.getLstSanPhamChiTietBySanPhamId(data.getSanPhamId());
			//button - mau sac add img
			List<MauSac> lstMauSacAddImg = mauSacService.getAllMauSacExistBySPId(data.getSanPhamId());
			HashMap<Long, String> lstMauSacAddImgHM = new HashMap<Long, String>();
			for (MauSac mauSac : lstMauSacAddImg) {
				lstMauSacAddImgHM.put(mauSac.getId(), mauSac.getTenMauSac());
			}
			data.setLstMauSacAddImg(lstMauSacAddImgHM);
			model.addAttribute("dataGen", dataGen);
			model.addAttribute("sanPhamManageDTO", data);
			return "admin/product/addProduct";
		}
		if (data.getGia().toString().isEmpty() || data.getTenSanPham().isEmpty() || data.getMoTa().isEmpty()
				|| data.getChatLieuId() == -1 || data.getLoaiSanPhamId() == -1 || data.getPhongCachId() == -1
				|| data.getKieuDangId() == -1) {
			model.addAttribute("messageDanger", "bạn không được để trống thông tin sản phẩm");
			List<SanPhamChiTiet> dataGen = sanPhamChiTietService.getLstSanPhamChiTietBySanPhamId(data.getSanPhamId());
			//button - mau sac add img
			List<MauSac> lstMauSacAddImg = mauSacService.getAllMauSacExistBySPId(data.getSanPhamId());
			HashMap<Long, String> lstMauSacAddImgHM = new HashMap<Long, String>();
			for (MauSac mauSac : lstMauSacAddImg) {
				lstMauSacAddImgHM.put(mauSac.getId(), mauSac.getTenMauSac());
			}
			data.setLstMauSacAddImg(lstMauSacAddImgHM);
			model.addAttribute("dataGen", dataGen);
			model.addAttribute("sanPhamManageDTO", data);
			return "admin/product/addProduct";
		}
		if(data.getGia().compareTo(BigDecimal.valueOf(1000L))<0) {
			model.addAttribute("messageDanger", "bạn không được để giá sản phẩm < 1000");
			List<SanPhamChiTiet> dataGen = sanPhamChiTietService.getLstSanPhamChiTietBySanPhamId(data.getSanPhamId());
			//button - mau sac add img
			List<MauSac> lstMauSacAddImg = mauSacService.getAllMauSacExistBySPId(data.getSanPhamId());
			HashMap<Long, String> lstMauSacAddImgHM = new HashMap<Long, String>();
			for (MauSac mauSac : lstMauSacAddImg) {
				lstMauSacAddImgHM.put(mauSac.getId(), mauSac.getTenMauSac());
			}
			data.setLstMauSacAddImg(lstMauSacAddImgHM);
			model.addAttribute("dataGen", dataGen);
			model.addAttribute("sanPhamManageDTO", data);
			return "admin/product/addProduct";
		}
		boolean isSuccess = false;
		Optional<SanPham> optSP = sanPhamService.findById(data.getSanPhamId());
		if (optSP.isPresent()) {
			optSP.get().setDaXoa(false);
			optSP.get().setGia(data.getGia());
			optSP.get().setTenSanPham(data.getTenSanPham());
			optSP.get().setMoTa(data.getMoTa());

			ChatLieu chatLieu = new ChatLieu();
			chatLieu.setId(data.getChatLieuId());
			optSP.get().setChatLieu(chatLieu);

			LoaiSanPham loaiSanPham = new LoaiSanPham();
			loaiSanPham.setId(data.getLoaiSanPhamId());
			optSP.get().setLoaiSanPham(loaiSanPham);

			PhongCach phongCach = new PhongCach();
			phongCach.setId(data.getPhongCachId());
			optSP.get().setPhongCach(phongCach);

			KieuDang kieuDang = new KieuDang();
			kieuDang.setId(data.getKieuDangId());
			optSP.get().setKieuDang(kieuDang);

			sanPhamService.save(optSP.get());
			isSuccess = true;
		}

		String[] soLuongs = request.getParameterValues("soLuongs");
		String[] ids = request.getParameterValues("soLuongTheoIds");
		if (soLuongs != null && ids != null) {
			for (String item : soLuongs) {
				if (!isNumeric(item)) {
					redirect.addFlashAttribute("messageDanger", "Số lượng phải là số");
					return "redirect:/admin/product/edit/"+data.getSanPhamId();
				}
				if(Integer.parseInt(item) < 1) {
					redirect.addFlashAttribute("messageDanger", "Số lượng phải lớn hơn 0");
					return "redirect:/admin/product/edit/"+data.getSanPhamId();
				}
			}
			for (String item : ids) {
				if (!isNumeric(item)) {
					redirect.addFlashAttribute("messageDanger", "Id phải là số");
					return "redirect:/admin/product/edit/"+data.getSanPhamId();
				}
			}
		}
		// add key-id, value-soLuong -> map
		Map<String, String> hm = new HashMap<>();
		for (int i = 0; i < ids.length; i++) {
			hm.put(ids[i], soLuongs[i]);
		}
		Long sPID = null;
		for (Map.Entry<String, String> mapItem : hm.entrySet()) {
			Optional<SanPhamChiTiet> opt = sanPhamChiTietService.findById(Long.parseLong(mapItem.getKey()));
			if (opt.isPresent()) {
				opt.get().setSoLuong(Integer.parseInt(mapItem.getValue()));
				sPID = opt.get().getSanPham().getId();
				sanPhamChiTietService.save(opt.get());
				isSuccess = true;
			} else
				isSuccess = false;
		}
		List<SanPhamChiTiet> dataGen = sanPhamChiTietService.getLstSanPhamChiTietBySanPhamId(sPID);
		model.addAttribute("dataGen", dataGen);
		//button - mau sac add img
		List<MauSac> lstMauSacAddImg = mauSacService.getAllMauSacExistBySPId(data.getSanPhamId());
		HashMap<Long, String> lstMauSacAddImgHM = new HashMap<Long, String>();
		for (MauSac mauSac : lstMauSacAddImg) {
			lstMauSacAddImgHM.put(mauSac.getId(), mauSac.getTenMauSac());
		}
		data.setLstMauSacAddImg(lstMauSacAddImgHM);
		List<Long> lstKC = dataGen.stream().map(i -> i.getKichCo().getId()).collect(Collectors.toList());
		List<Long> lstMS = dataGen.stream().map(i -> i.getMauSac().getId()).collect(Collectors.toList());
		data.setKichCoIds(lstKC);
		data.setMauSacIds(lstMS);

		List<HinhAnh> lstHinhAnh = hinhAnhService.getLstHinhAnhMauSacBySanPhamId(data.getSanPhamId());
		List<List<HinhAnhDTO>> lstHinhAnhDTOs = new ArrayList<>();
		int i = 0;
		int j = 0;
		int countLstHinhAnh = 0;
		do {
			List<HinhAnhDTO> HinhAnhDTOs = new ArrayList<>();
			for (j = i; j < lstHinhAnh.size(); j++) {
				if (lstHinhAnh.get(j).getMauSac().getId().equals(lstHinhAnh.get(i).getMauSac().getId())) {
					HinhAnhDTO haDto = new HinhAnhDTO();
					BeanUtils.copyProperties(lstHinhAnh.get(j), haDto);
					haDto.setCoHienThi(lstHinhAnh.get(j).getCoHienThi());
					haDto.setLaAnhChinh(lstHinhAnh.get(j).getLaAnhChinh());
					haDto.setHinhAnhid(lstHinhAnh.get(j).getId());
					HinhAnhDTOs.add(haDto);
				} else {
					i = j;
					break;
				}
			}
			countLstHinhAnh++;

			lstHinhAnhDTOs.add(HinhAnhDTOs);
			if (j == lstHinhAnh.size())
				break;
		} while (j < lstHinhAnh.size());
		int m = 0;
		List<HinhAnhMauSacDTO> lstHinhAnhMauSacDTO = new ArrayList<>();
		List<Long> lstHinhAnhDistinct = hinhAnhService.getDistinctMauSacInHinhAnhBySanPhamId(data.getSanPhamId());
		if (lstHinhAnhDistinct.size() != 0) {
			for (Long mauSacId : lstHinhAnhDistinct) {
				HinhAnhMauSacDTO hinhAnhMauSacDTO = new HinhAnhMauSacDTO();
				Optional<MauSac> optMS = mauSacService.findById(mauSacId);
				if (optMS.isPresent()) {
					hinhAnhMauSacDTO.setTenMauSacAddImg(optMS.get().getTenMauSac());
					hinhAnhMauSacDTO.setMauSacAddImagesId(mauSacId);
				}
				if (m < countLstHinhAnh) {
					List<HinhAnhDTO> lstHinhAnhDTO = new ArrayList<HinhAnhDTO>();
					for (HinhAnhDTO item : lstHinhAnhDTOs.get(m)) {
						lstHinhAnhDTO.add(item);
					}
					hinhAnhMauSacDTO.setHinhAnhDTOs(lstHinhAnhDTO);
					m++;
				}
				lstHinhAnhMauSacDTO.add(hinhAnhMauSacDTO);
			}
			data.setLstHinhAnhMauSacDTO(lstHinhAnhMauSacDTO);
		}
		if (isSuccess == true) {
			model.addAttribute("messageSuccess", "Cập nhật thông tin sản phẩm thành công");
		} else
			model.addAttribute("messageDanger", "Cập nhật sản phẩm thất bại");
		model.addAttribute("sanPhamManageDTO", data);
		model.addAttribute("dataGen", dataGen);
		return "admin/product/addProduct";
	}

	@GetMapping("/productDetail/edit/{id}/{pageName}")
	public String editProductDetail(ModelMap model, @PathVariable("id") Long id,
			@PathVariable("pageName") String returnUrlPage) {
		Optional<SanPhamChiTiet> opt = sanPhamChiTietService.findById(id);
		if (opt.isPresent()) {
			SanPhamChiTietDTO dto = new SanPhamChiTietDTO();
			BeanUtils.copyProperties(opt.get(), dto);
			dto.setKichCoId(opt.get().getKichCo().getId());
			dto.setMauSacId(opt.get().getMauSac().getId());
			dto.setSanPhamId(opt.get().getSanPham().getId());
			if (returnUrlPage.equalsIgnoreCase(pageContants.addProduct)) {
				returnUrlPage = "/admin/product/edit/" + opt.get().getSanPham().getId();
			} else if (returnUrlPage.equalsIgnoreCase(pageContants.infoProduct)) {
				returnUrlPage = "/admin/product/info/" + opt.get().getSanPham().getId();
			}
			model.addAttribute("sanPhamChiTietDTO", dto);
			model.addAttribute("pageName", returnUrlPage);
		}
		return "admin/product/editProductDetail";
	}

	@GetMapping("addImageProductDetail/{mauSacIdInput}")
	public String addImageProductDetail(Model model, @ModelAttribute("sanPhamManageDTO") SanPhamManageDTO data,
			HttpServletRequest request, final RedirectAttributes redirect,
			@PathVariable("mauSacIdInput") Long mauSacIdInput) throws IOException {
		String[] spctIds = request.getParameterValues("mauSacProductIds");
		long idSanPham = 0;
		if (spctIds != null) {
			for (String id : spctIds) {
				if (isNumeric(id)) {
					idSanPham = Long.parseLong(id);
					break;
				}
			}
		}
		int count = hinhAnhService.getCountHinhAnhBySanPhamId(idSanPham);
		if(count > 0) {
			data.setIsHaveImg(true);
		}else data.setIsHaveImg(false);
		
		int countHinhAnhToiDaDuocThem = hinhAnhService.getCountHinhAnhChoPhepThemBySanPhamId(idSanPham);
		int soLuongHinhAnhHienCo = hinhAnhService.getCountHinhAnhBySanPhamId(idSanPham);
		if(countHinhAnhToiDaDuocThem-soLuongHinhAnhHienCo>0) {
			int hinhAnhCoTheThem = countHinhAnhToiDaDuocThem-soLuongHinhAnhHienCo;
			model.addAttribute("messageSuccess", "Số lượng hình ảnh có thể thêm là: "+hinhAnhCoTheThem+" ảnh");
			if(mauSacIdInput.equals(Long.parseLong("0"))) {
				List<MauSac> lstMauSacAddImg = mauSacService.getAllMauSacExistBySPId(idSanPham);
				List<HinhAnhMauSacDTO> lstHinhAnhMauSacDTO = new ArrayList<HinhAnhMauSacDTO>();
				for (MauSac mauSac : lstMauSacAddImg) {
					List<Long> spctIdsAddImg = new ArrayList<Long>();
					mauSac.getSanPhamChiTiets().stream().forEach(i -> {
						spctIdsAddImg.add(i.getId());
					});
					HinhAnhMauSacDTO dto = new HinhAnhMauSacDTO();
					dto.setMauSacAddImagesId(mauSac.getId());
					dto.setTenMauSacAddImg(mauSac.getTenMauSac());
					lstHinhAnhMauSacDTO.add(dto);
				}
				data.setLstHinhAnhMauSacDTO(lstHinhAnhMauSacDTO);
				data.setIsCreatedImg(true);
				data.setIsCreatedValueImg(false);
				model.addAttribute("sanPhamManageDTO", data);
				List<SanPhamChiTiet> dataGen = sanPhamChiTietService.getLstSanPhamChiTietBySanPhamId(idSanPham);
				model.addAttribute("dataGen", dataGen);
				//button - mau sac add img
				HashMap<Long, String> lstMauSacAddImgHM = new HashMap<Long, String>();
				for (MauSac mauSac : lstMauSacAddImg) {
					lstMauSacAddImgHM.put(mauSac.getId(), mauSac.getTenMauSac());
				}
				data.setLstMauSacAddImg(lstMauSacAddImgHM);
				return "admin/product/addProduct";
			}else {
				Optional<MauSac> optMS = mauSacService.findById(mauSacIdInput);
				List<HinhAnhMauSacDTO> lstHinhAnhMauSacDTO = new ArrayList<HinhAnhMauSacDTO>();
				if(optMS.isPresent()) {
					List<Long> spctIdsAddImg = new ArrayList<Long>();
					optMS.get().getSanPhamChiTiets().stream().forEach(i -> {
						spctIdsAddImg.add(i.getId());
					});
					HinhAnhMauSacDTO dto = new HinhAnhMauSacDTO();
					dto.setMauSacAddImagesId(optMS.get().getId());
					dto.setTenMauSacAddImg(optMS.get().getTenMauSac());
					lstHinhAnhMauSacDTO.add(dto);
				}
				data.setLstHinhAnhMauSacDTO(lstHinhAnhMauSacDTO);
				data.setIsCreatedImg(true);
				data.setIsCreatedValueImg(false);
				model.addAttribute("sanPhamManageDTO", data);
				List<SanPhamChiTiet> dataGen = sanPhamChiTietService.getLstSanPhamChiTietBySanPhamId(idSanPham);
				model.addAttribute("dataGen", dataGen);
				List<MauSac> lstMauSacAddImg = mauSacService.getAllMauSacExistBySPId(idSanPham);
				//button - mau sac add img
				HashMap<Long, String> lstMauSacAddImgHM = new HashMap<Long, String>();
				for (MauSac mauSac : lstMauSacAddImg) {
					lstMauSacAddImgHM.put(mauSac.getId(), mauSac.getTenMauSac());
				}
				data.setLstMauSacAddImg(lstMauSacAddImgHM);
				return "admin/product/addProduct";
			}
		}else {
			redirect.addFlashAttribute("messageDanger", "Số lượng hình ảnh đang đạt tối đa ("+soLuongHinhAnhHienCo+" ảnh)");
			return "redirect:/admin/product/edit/"+idSanPham;
		}
	}

	@PostMapping("saveImageProductDetail")
	public String saveImageProductDetail(ModelMap model,
			@ModelAttribute("sanPhamManageDTO") SanPhamManageDTO sanPhamManageDTO,
			RedirectAttributes redirect) {
		for (HinhAnhMauSacDTO item : sanPhamManageDTO.getLstHinhAnhMauSacDTO()) {
			for (MultipartFile imgFile : item.getImgFiles()) {
				if (imgFile.isEmpty()) {
					model.addAttribute("messageDanger", "Hình ảnh cho sản phẩm không được để trống");
					List<SanPhamChiTiet> dataGen = sanPhamChiTietService
							.getLstSanPhamChiTietBySanPhamId(sanPhamManageDTO.getSanPhamId());
					sanPhamManageDTO.setIsAddProductImageSuccess(true);
					model.addAttribute("dataGen", dataGen);
					//button - mau sac add img
					List<MauSac> lstMauSacAddImg = mauSacService.getAllMauSacExistBySPId(sanPhamManageDTO.getSanPhamId());
					HashMap<Long, String> lstMauSacAddImgHM = new HashMap<Long, String>();
					for (MauSac mauSac : lstMauSacAddImg) {
						lstMauSacAddImgHM.put(mauSac.getId(), mauSac.getTenMauSac());
					}
					model.addAttribute("sanPhamManageDTO", sanPhamManageDTO);
					return "admin/product/addProduct";
				}
			}
		}
		int countHinhAnh = hinhAnhService.getCountHinhAnhBySanPhamId(sanPhamManageDTO.getSanPhamId());
		int countHinhAnhChoPhepThem = hinhAnhService.getCountHinhAnhChoPhepThemBySanPhamId(sanPhamManageDTO.getSanPhamId());
		int soLuongHinhAnhThem = sanPhamManageDTO.getLstHinhAnhMauSacDTO().stream().map(HinhAnhMauSacDTO::getImgFiles)
				.filter(rs -> rs != null)
				.mapToInt(List::size).sum();
		if(countHinhAnhChoPhepThem - countHinhAnh - soLuongHinhAnhThem >= 0 && countHinhAnhChoPhepThem - soLuongHinhAnhThem >= 0) {
			sanPhamManageDTO.getLstHinhAnhMauSacDTO().stream().forEach(i -> {
				Optional<MauSac> optMS = mauSacService.findById(i.getMauSacAddImagesId());
				Optional<SanPham> optSP = sanPhamService.findById(sanPhamManageDTO.getSanPhamId());
				i.getImgFiles().stream().forEach(img -> {
					UUID uuid = UUID.randomUUID();
					String uuString = uuid.toString();
					HinhAnh hinhAnh = new HinhAnh();
					hinhAnh.setTenAnh(storageService.getStoredFileName(img, uuString));
					if (optSP.isPresent()) {
						hinhAnh.setSanPham(optSP.get());
					}
					storageService.store(img, hinhAnh.getTenAnh());
					if (optMS.isPresent()) {
						List<HinhAnh> lstHA = hinhAnhService
								.getHinhAnhBySanPhamIdAndMauSacId(sanPhamManageDTO.getSanPhamId(), optMS.get().getId());
						for (HinhAnh hinhAnhOld : lstHA) {
							hinhAnhOld.setLaAnhChinh(false);
							hinhAnhService.save(hinhAnhOld);
						}
						hinhAnh.setMauSac(optMS.get());
						hinhAnh.setCoHienThi(true);
						hinhAnh.setLaAnhChinh(true);
						hinhAnhService.save(hinhAnh);
					}
				});
			});
		}else {
			model.addAttribute("messageDanger", "Số lượng hình ảnh còn lại cho phép thêm của sản phẩm là: "+ (countHinhAnhChoPhepThem - countHinhAnh));
			List<SanPhamChiTiet> dataGen = sanPhamChiTietService
					.getLstSanPhamChiTietBySanPhamId(sanPhamManageDTO.getSanPhamId());
			sanPhamManageDTO.setIsAddProductImageSuccess(true);
			model.addAttribute("dataGen", dataGen);
			//button - mau sac add img
			List<MauSac> lstMauSacAddImg = mauSacService.getAllMauSacExistBySPId(sanPhamManageDTO.getSanPhamId());
			HashMap<Long, String> lstMauSacAddImgHM = new HashMap<Long, String>();
			for (MauSac mauSac : lstMauSacAddImg) {
				lstMauSacAddImgHM.put(mauSac.getId(), mauSac.getTenMauSac());
			}
			model.addAttribute("sanPhamManageDTO", sanPhamManageDTO);
			return "admin/product/addProduct";
		}

		List<HinhAnh> lstHinhAnh = hinhAnhService.getLstHinhAnhMauSacBySanPhamId(sanPhamManageDTO.getSanPhamId());
		List<List<HinhAnhDTO>> lstHinhAnhDTOs = new ArrayList<>();
		int i = 0;
		int j = 0;
		int countLstHinhAnh = 0;
		do {
			List<HinhAnhDTO> HinhAnhDTOs = new ArrayList<>();
			for (j = i; j < lstHinhAnh.size(); j++) {
				if (lstHinhAnh.get(j).getMauSac().getId().equals(lstHinhAnh.get(i).getMauSac().getId())) {
					HinhAnhDTO haDto = new HinhAnhDTO();
					BeanUtils.copyProperties(lstHinhAnh.get(j), haDto);
					haDto.setCoHienThi(lstHinhAnh.get(j).getCoHienThi());
					haDto.setLaAnhChinh(lstHinhAnh.get(j).getLaAnhChinh());
					haDto.setHinhAnhid(lstHinhAnh.get(j).getId());
					HinhAnhDTOs.add(haDto);
				} else {
					i = j;
					break;
				}
			}
			countLstHinhAnh++;

			lstHinhAnhDTOs.add(HinhAnhDTOs);
			if (j == lstHinhAnh.size())
				break;
		} while (j < lstHinhAnh.size());
		int m = 0;
		List<HinhAnhMauSacDTO> lstHinhAnhMauSacDTO = new ArrayList<>();
		List<Long> lstHinhAnhDistinct = hinhAnhService
				.getDistinctMauSacInHinhAnhBySanPhamId(sanPhamManageDTO.getSanPhamId());
		for (Long mauSacId : lstHinhAnhDistinct) {
			HinhAnhMauSacDTO hinhAnhMauSacDTO = new HinhAnhMauSacDTO();
			Optional<MauSac> optMS = mauSacService.findById(mauSacId);
			if (optMS.isPresent()) {
				hinhAnhMauSacDTO.setTenMauSacAddImg(optMS.get().getTenMauSac());
				hinhAnhMauSacDTO.setMauSacAddImagesId(mauSacId);
			}
			if (m < countLstHinhAnh) {
				List<HinhAnhDTO> lstHinhAnhDTO = new ArrayList<HinhAnhDTO>();
				for (HinhAnhDTO item : lstHinhAnhDTOs.get(m)) {
					lstHinhAnhDTO.add(item);
				}
				hinhAnhMauSacDTO.setHinhAnhDTOs(lstHinhAnhDTO);
				m++;
			}
			lstHinhAnhMauSacDTO.add(hinhAnhMauSacDTO);
		}
		sanPhamManageDTO.setIsCreatedImg(false);
		sanPhamManageDTO.setIsCreatedValueImg(true);
		sanPhamManageDTO.setLstHinhAnhMauSacDTO(lstHinhAnhMauSacDTO);
		List<SanPhamChiTiet> dataGen = sanPhamChiTietService
				.getLstSanPhamChiTietBySanPhamId(sanPhamManageDTO.getSanPhamId());
		sanPhamManageDTO.setIsAddProductImageSuccess(true);
		model.addAttribute("dataGen", dataGen);
		//button - mau sac add img
		List<MauSac> lstMauSacAddImg = mauSacService.getAllMauSacExistBySPId(sanPhamManageDTO.getSanPhamId());
		HashMap<Long, String> lstMauSacAddImgHM = new HashMap<Long, String>();
		for (MauSac mauSac : lstMauSacAddImg) {
			lstMauSacAddImgHM.put(mauSac.getId(), mauSac.getTenMauSac());
		}
		sanPhamManageDTO.setLstMauSacAddImg(lstMauSacAddImgHM);
		model.addAttribute("sanPhamManageDTO", sanPhamManageDTO);
		if(!sanPhamManageDTO.getIsEdit()) {
			redirect.addFlashAttribute("messageSuccess", "Thêm sản phẩm thành công");
			return "redirect:/admin/product";
		}else {
			model.addAttribute("messageSuccess", "Thêm hình ảnh cho sản phẩm thành công");
			return "/admin/product/addProduct";
		}
	}

	@GetMapping("changeCoHienThi/{imgName}")
	@ResponseBody
	public ResponseEntity<String> changeCoHienThi(@PathVariable("imgName") String imgName) {
		Optional<HinhAnh> opt = hinhAnhService.getHinhAnhByName(imgName);
		if (opt.isPresent()) {
			if(opt.get().getLaAnhChinh() == true) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}else {
				opt.get().setCoHienThi(!opt.get().getCoHienThi());
				hinhAnhService.save(opt.get());
				return new ResponseEntity<>(HttpStatus.OK);
			}
		} else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("changeLaAnhChinh/{imgName}")
	@ResponseBody
	public ResponseEntity<String> changeLaAnhChinh(ModelMap model, @PathVariable("imgName") String imgName) {
		Optional<HinhAnh> opt = hinhAnhService.getHinhAnhByName(imgName);
		if (opt.isPresent()) {
			int countHAChinhOld = hinhAnhService.getCountHinhAnhChinhBySanPhamIdAndMauSacId(
					opt.get().getSanPham().getId(), opt.get().getMauSac().getId());
			if (countHAChinhOld > 0) {
				Optional<HinhAnh> optHAChinhOld = hinhAnhService.getHinhAnhChinhBySanPhamIdAndMauSacId(
						opt.get().getSanPham().getId(), opt.get().getMauSac().getId());
				if (optHAChinhOld.isPresent()) {
					optHAChinhOld.get().setLaAnhChinh(false);
					hinhAnhService.save(optHAChinhOld.get());
				}
			}
			opt.get().setLaAnhChinh(true);
			opt.get().setCoHienThi(true);
			hinhAnhService.save(opt.get());
			return new ResponseEntity<>(HttpStatus.OK);

		} else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("removeHinhAnh/{imgName}")
	@ResponseBody
	public ResponseEntity<String> removeHinhAnh(ModelMap model, @PathVariable("imgName") String imgName)
			throws IOException {
		Optional<HinhAnh> opt = hinhAnhService.getHinhAnhByName(imgName);
		if (opt.isPresent()) {
			if(opt.get().getLaAnhChinh() == true) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}else {
				if (!opt.get().getTenAnh().isEmpty()) {
					storageService.delete(opt.get().getTenAnh());
					hinhAnhService.delete(opt.get());
				}
				hinhAnhService.delete(opt.get());
				return new ResponseEntity<>(HttpStatus.OK);
			}
		} else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping("saveValueImageProduct")
	public ModelAndView saveProduct(ModelMap model,
			@ModelAttribute("sanPhamManageDTO") SanPhamManageDTO sanPhamManageDTO, HttpServletRequest request) {
		if (!sanPhamManageDTO.getLstHinhAnhMauSacDTO().isEmpty()) {
			sanPhamManageDTO.getLstHinhAnhMauSacDTO().stream().forEach(i1 -> {
				if (!i1.getHinhAnhDTOs().isEmpty()) {
					i1.getHinhAnhDTOs().stream().forEach(i2 -> {
						Optional<HinhAnh> opt = hinhAnhService.findById(i2.getHinhAnhid());
						if (opt.isPresent()) {
//							opt.get().setCoHienThi(i2.getCoHienThi());
//							opt.get().setLaAnhChinh(i2.getLaAnhChinh());
							hinhAnhService.save(opt.get());
						}
					});
				}
			});
		}
		List<HinhAnh> lstHinhAnh = hinhAnhService.getLstHinhAnhMauSacBySanPhamId(sanPhamManageDTO.getSanPhamId());
		List<List<HinhAnhDTO>> lstHinhAnhDTOs = new ArrayList<>();
		int i = 0;
		int j = 0;
		int countLstHinhAnh = 0;
		do {
			List<HinhAnhDTO> HinhAnhDTOs = new ArrayList<>();
			for (j = i; j < lstHinhAnh.size(); j++) {
				if (lstHinhAnh.get(j).getMauSac().getId().equals(lstHinhAnh.get(i).getMauSac().getId())) {
					HinhAnhDTO haDto = new HinhAnhDTO();
					BeanUtils.copyProperties(lstHinhAnh.get(j), haDto);
					haDto.setHinhAnhid(lstHinhAnh.get(j).getId());
					haDto.setCoHienThi(lstHinhAnh.get(j).getCoHienThi());
					haDto.setLaAnhChinh(lstHinhAnh.get(j).getLaAnhChinh());
					HinhAnhDTOs.add(haDto);
				} else {
					i = j;
					break;
				}
			}
			countLstHinhAnh++;

			lstHinhAnhDTOs.add(HinhAnhDTOs);
			if (j == lstHinhAnh.size())
				break;
		} while (j < lstHinhAnh.size());
		int m = 0;
		List<HinhAnhMauSacDTO> lstHinhAnhMauSacDTO = new ArrayList<>();
		List<Long> lstHinhAnhDistinct = hinhAnhService
				.getDistinctMauSacInHinhAnhBySanPhamId(sanPhamManageDTO.getSanPhamId());
		for (Long mauSacId : lstHinhAnhDistinct) {
			HinhAnhMauSacDTO hinhAnhMauSacDTO = new HinhAnhMauSacDTO();
			Optional<MauSac> optMS = mauSacService.findById(mauSacId);
			if (optMS.isPresent()) {
				hinhAnhMauSacDTO.setTenMauSacAddImg(optMS.get().getTenMauSac());
				hinhAnhMauSacDTO.setMauSacAddImagesId(mauSacId);
			}
			if (m < countLstHinhAnh) {
//				Resource[] imgfiles = applicationContext.getre;
				List<HinhAnhDTO> lstHinhAnhDTO = new ArrayList<HinhAnhDTO>();
				for (HinhAnhDTO item : lstHinhAnhDTOs.get(m)) {
					lstHinhAnhDTO.add(item);
				}
				hinhAnhMauSacDTO.setHinhAnhDTOs(lstHinhAnhDTO);
				m++;
			}
			lstHinhAnhMauSacDTO.add(hinhAnhMauSacDTO);
		}
		sanPhamManageDTO.setIsCreatedImg(false);
		sanPhamManageDTO.setIsCreatedValueImg(true);
		sanPhamManageDTO.setLstHinhAnhMauSacDTO(lstHinhAnhMauSacDTO);
		List<SanPhamChiTiet> dataGen = sanPhamChiTietService
				.getLstSanPhamChiTietBySanPhamId(sanPhamManageDTO.getSanPhamId());
		model.addAttribute("sanPhamManageDTO", sanPhamManageDTO);
		model.addAttribute("dataGen", dataGen);
		//button - mau sac add img
		List<MauSac> lstMauSacAddImg = mauSacService.getAllMauSacExistBySPId(sanPhamManageDTO.getSanPhamId());
		HashMap<Long, String> lstMauSacAddImgHM = new HashMap<Long, String>();
		for (MauSac mauSac : lstMauSacAddImg) {
			lstMauSacAddImgHM.put(mauSac.getId(), mauSac.getTenMauSac());
		}
		sanPhamManageDTO.setLstMauSacAddImg(lstMauSacAddImgHM);
		return new ModelAndView("admin/product/addProduct");
	}

	@PostMapping("saveOptionValue")
	public String saveOptionValue(ModelMap model, RedirectAttributes redirect, @ModelAttribute("sanPhamManageDTO") SanPhamManageDTO sanPhamManageDTO,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		String[] value = request.getParameterValues("thuocTinhInput");
		String[] option = request.getParameterValues("fieldthuocTinhInput");
		List<SanPhamChiTiet> dataGen = sanPhamChiTietService
				.getLstSanPhamChiTietBySanPhamId(sanPhamManageDTO.getSanPhamId());
		model.addAttribute("dataGen", dataGen);
		//button - mau sac add img
		List<MauSac> lstMauSacAddImg = mauSacService.getAllMauSacExistBySPId(sanPhamManageDTO.getSanPhamId());
		HashMap<Long, String> lstMauSacAddImgHM = new HashMap<Long, String>();
		for (MauSac mauSac : lstMauSacAddImg) {
			lstMauSacAddImgHM.put(mauSac.getId(), mauSac.getTenMauSac());
		}
		sanPhamManageDTO.setLstMauSacAddImg(lstMauSacAddImgHM);
		model.addAttribute("sanPhamManageDTO", sanPhamManageDTO);
		if (value != null && option != null) {
			if (!option[0].isEmpty() && !value[0].isEmpty()) {
				if (option[0].equalsIgnoreCase(OptionContants.chatLieu)) {
					ChatLieu entity = new ChatLieu();
					entity.setTenChatLieu(value[0].toString());
					chatLieuService.save(entity);
					List<ChatLieu> loadData = chatLieuService.selectAllChatLieuExist();
					model.addAttribute("lstChatLieu", loadData);
					model.addAttribute("messageSuccess", "Thêm mới chất liệu thành công");
					redirect.addFlashAttribute("messageSuccess", "Thêm mới chất liệu thành công");
				} else if (option[0].equalsIgnoreCase(OptionContants.loaiSanPham)) {
					LoaiSanPham entity = new LoaiSanPham();
					entity.setTenLoaiSanPham(value[0].toString());
					loaiSanPhamService.save(entity);
					List<LoaiSanPham> loadData = loaiSanPhamService.selectAllLoaiHangExist();
					model.addAttribute("lstLoaiSanPham", loadData);
					model.addAttribute("messageSuccess", "Thêm mới loại sản phẩm thành công");
					redirect.addFlashAttribute("messageSuccess", "Thêm mới loại sản phẩm thành công");
				} else if (option[0].equalsIgnoreCase(OptionContants.kichCo)) {
					KichCo entity = new KichCo();
					entity.setTenKichCo(value[0].toString());
					kichCoService.save(entity);
					List<KichCo> loadData = kichCoService.selectAllKichCoExist();
					model.addAttribute("lstKichCo", loadData);
					model.addAttribute("messageSuccess", "Thêm mới kích cỡ thành công");
					redirect.addFlashAttribute("messageSuccess", "Thêm mới kích cỡ thành công");
				} else if (option[0].equalsIgnoreCase(OptionContants.kieuDang)) {
					KieuDang entity = new KieuDang();
					entity.setTenKieuDang(value[0].toString());
					kieuDangService.save(entity);
					List<KieuDang> loadData = kieuDangService.selectAllKieuDangExist();
					model.addAttribute("lstKieuDang", loadData);
					model.addAttribute("messageSuccess", "Thêm mới kiểu dáng thành công");
					redirect.addFlashAttribute("messageSuccess", "Thêm mới kiểu dáng thành công");
				} else if (option[0].equalsIgnoreCase(OptionContants.phongCach)) {
					PhongCach entity = new PhongCach();
					entity.setTenPhongCach(value[0].toString());
					phongCachService.save(entity);
					List<PhongCach> loadData = phongCachService.selectAllPhongCachExist();
					model.addAttribute("lstPhongCach", loadData);
					model.addAttribute("messageSuccess", "Thêm mới phong cách thành công");
					redirect.addFlashAttribute("messageSuccess", "Thêm mới phong cách thành công");
				} else if (option[0].equalsIgnoreCase(OptionContants.mauSac)) {
					String[] maMauSac = request.getParameterValues("maMauSac");
					if (!maMauSac[0].isEmpty()) {
						MauSac entity = new MauSac();
						entity.setTenMauSac(value[0].toString());
						entity.setMaMauSac(maMauSac[0].toString());
						mauSacService.save(entity);
						List<MauSac> loadData = mauSacService.selectAllMauSacExist();
						model.addAttribute("lstMauSac", loadData);
						model.addAttribute("messageSuccess", "Thêm mới màu sắc thành công");
						redirect.addFlashAttribute("messageSuccess", "Thêm mới màu sắc thành công");
					} else {
						model.addAttribute("messageDanger", "Mã màu sắc không được để trống");
						return "admin/product/addProduct";
					}
				}
			} else {
				model.addAttribute("messageDanger", "Tên giá trị thuộc tính không được để trống");
			}
		} else {
			model.addAttribute("messageDanger", "Lưu giá trị thuộc tính sản phẩm thất bại");
		}
		if(sanPhamManageDTO.getSanPhamId() == null) {
			 return "admin/product/addProduct";
		}else return "redirect:/admin/product/edit/"+sanPhamManageDTO.getSanPhamId();
	}

	@GetMapping("delete/{id}")
	public String deleteProduct(@PathVariable("id") Long sanPhamId, ModelMap model,
			@ModelAttribute("sanPhamManageDTO") Optional<SanPhamManageDTO> sanPhamManageDTO,
			@ModelAttribute("dataSearch") SPAndSPCTSearchDto dataSearch, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size, final RedirectAttributes redirectAttributes)
			throws IOException {
		Optional<SanPham> opt = sanPhamService.findById(sanPhamId);
		if (opt.isPresent()) {
			List<HinhAnh> lstHinhAnh = hinhAnhService.getHinhAnhBySanPhamId(sanPhamId);
			for (HinhAnh ha : lstHinhAnh) {
				if (!ha.getTenAnh().isEmpty()) {
					storageService.delete(ha.getTenAnh());
					hinhAnhService.delete(ha);
				} else {
					redirectAttributes.addFlashAttribute("messageDanger",
							"Tên hình ảnh: '" + ha.getTenAnh() + "' không tồn tại");
				}
			}
			sanPhamService.delete(opt.get());
			for (SanPhamChiTiet spct : opt.get().getSanPhamChiTiets()) {
				sanPhamChiTietService.delete(spct);
			}
			redirectAttributes.addFlashAttribute("messageSuccess", "Xóa sản phẩm thành công");
			redirectAttributes.addFlashAttribute("page", page);
			redirectAttributes.addFlashAttribute("size", size);
			redirectAttributes.addFlashAttribute("dataSearch", dataSearch);
			return "redirect:/admin/product";
		} else {
			redirectAttributes.addFlashAttribute("dataSearch", dataSearch);
			redirectAttributes.addFlashAttribute("messageDanger", "Xóa sản phẩm thất bại");
			return "redirect:/admin/product";
		}
	}

	@PostMapping("deleteAllByIdsAddProduct")
	public String deleteAllByIdsAddProduct(final RedirectAttributes redirect,
			@ModelAttribute("sanPhamManageDTO") SanPhamManageDTO sanPhamManageDTO, HttpServletRequest request)
			throws IOException {
		String[] ids = request.getParameterValues("productDetailIds");
		if (ids != null) {
			for (String item : ids) {
				Optional<SanPhamChiTiet> opt = sanPhamChiTietService.findById(Long.parseLong(item));
				if (opt.isPresent()) {
					sanPhamChiTietService.delete(opt.get());
					if(sanPhamManageDTO.getLstHinhAnhMauSacDTO() != null) {
						for (HinhAnhMauSacDTO hinhAnhMauSacDTO : sanPhamManageDTO.getLstHinhAnhMauSacDTO()) {
							int countHinhAnh = sanPhamChiTietService.getCountSanPhamChiTietExistBySanPhamIdAndMauSacId(
									sanPhamManageDTO.getSanPhamId(), hinhAnhMauSacDTO.getMauSacAddImagesId());
							if (countHinhAnh == 0) {
								List<HinhAnh> lstHA = hinhAnhService.getHinhAnhBySanPhamIdAndMauSacId(
										sanPhamManageDTO.getSanPhamId(), hinhAnhMauSacDTO.getMauSacAddImagesId());
								for (HinhAnh hinhAnh : lstHA) {
									if (!hinhAnh.getTenAnh().isEmpty()) {
										storageService.delete(hinhAnh.getTenAnh());
										hinhAnhService.delete(hinhAnh);
									} else {
										redirect.addFlashAttribute("messageDanger",
												"Tên hình ảnh: '" + hinhAnh.getTenAnh() + "' không tồn tại");
									}
								}
							}
						}
					}
					int countSPCTExist = sanPhamChiTietService
							.getCountSanPhamChiTietExistBySanPhamId(sanPhamManageDTO.getSanPhamId());
					if (countSPCTExist == 0) {
						Optional<SanPham> optSP = sanPhamService.findById(sanPhamManageDTO.getSanPhamId());
						sanPhamService.delete(optSP.get());
						redirect.addFlashAttribute("sanPhamManageDTO", sanPhamManageDTO);
					}
					redirect.addFlashAttribute("messageSuccess", "Xóa sản phẩm chi tiết thành công");
				} else {
					redirect.addFlashAttribute("messageDanger", "Xóa sản phẩm chi tiết thất bại");
				}
			}
		} else {
			redirect.addFlashAttribute("messageDanger", "Xóa sản phẩm chi tiết thất bại");
		}
		return "redirect:/admin/product/edit/" + sanPhamManageDTO.getSanPhamId();
	}

	@PostMapping("genProductDetails")
	public String genProductDetails(ModelMap model, @ModelAttribute("sanPhamManageDTO") SanPhamManageDTO data,
			HttpServletRequest request, RedirectAttributes redirect) {
		String[] kichCoIdsInProductDetail = request.getParameterValues("kichCoIdsInProductDetail");
		String[] mauSacIdsInProductDetail = request.getParameterValues("mauSacIdsInProductDetail");
		String[] soLuongInProductDetail = request.getParameterValues("soLuongInProductDetail");
		int countDuplicate = 0;
		int countNotDuplicate = 0;
		if (kichCoIdsInProductDetail != null && mauSacIdsInProductDetail != null && soLuongInProductDetail != null) {
			Optional<SanPham> optSP = sanPhamService.findById(data.getSanPhamId());
			if (optSP.isPresent()) {
				optSP.get().setDaXoa(false);
				sanPhamService.save(optSP.get());
				for (String kichCoId : kichCoIdsInProductDetail) {
					if(!kichCoId.isEmpty()) {
						for (String mauSacId : mauSacIdsInProductDetail) {
							if(!mauSacId.isEmpty()) {
								int countSPCTDuplicate = sanPhamChiTietService.selectCountSanPhamChiTietDuplicate(
										Long.parseLong(mauSacId), Long.parseLong(kichCoId), data.getSanPhamId());
								if (countSPCTDuplicate == 0) {
									SanPhamChiTiet spct = new SanPhamChiTiet();
									spct.setCoHienThi(true);
									spct.setDaXoa(false);
									spct.setSanPham(optSP.get());
									if(!soLuongInProductDetail[0].isEmpty()) {
										int soLuong = Integer.parseInt(soLuongInProductDetail[0]);
										spct.setSoLuong(soLuong);
									}else {
										redirect.addFlashAttribute("messageDanger", "Số lượng không được để trống");
										return "redirect:/admin/product/edit/" + data.getSanPhamId();
									}

									KichCo kichCo = new KichCo();
									kichCo.setId(Long.parseLong(kichCoId));
									spct.setKichCo(kichCo);

									MauSac mauSac = new MauSac();
									mauSac.setId(Long.parseLong(mauSacId));
									spct.setMauSac(mauSac);
									sanPhamChiTietService.save(spct);
									countNotDuplicate++;
								}else {
									countDuplicate++;
								}
							}else {
								redirect.addFlashAttribute("messageDanger", "Màu sắc không được để trống");
								return "redirect:/admin/product/edit/" + data.getSanPhamId();
							}
						}
					}else {
						redirect.addFlashAttribute("messageDanger", "Kích cỡ không được để trống");
						return "redirect:/admin/product/edit/" + data.getSanPhamId();
					}
				}
			}
		}else {
			redirect.addFlashAttribute("messageDanger", "Thêm sản phẩm chi tiết thất bại");
			return "redirect:/admin/product/edit/" + data.getSanPhamId();
		}
		if(countDuplicate>0) {
			redirect.addFlashAttribute("messageDanger", "Có "+countDuplicate+" Sản phẩm chi tiết đã tồn tại");
		}
		if(countNotDuplicate>0) {
			redirect.addFlashAttribute("messageSuccess", "Thêm "+countNotDuplicate+" sản phẩm chi tiết thành công");
		}
		return "redirect:/admin/product/edit/"+data.getSanPhamId();
	}

	@GetMapping("deleteProductDetail/{id}")
	public String deleteProductDetail(@PathVariable("id") Long sanPhamChiTietId, ModelMap model,
			final RedirectAttributes redirect, @ModelAttribute("sanPhamManageDTO") SanPhamManageDTO sanPhamManageDTO)
			throws IOException {
		Optional<SanPhamChiTiet> opt = sanPhamChiTietService.findById(sanPhamChiTietId);
		boolean idSuccess = false;
		if (opt.isPresent()) {
			sanPhamChiTietService.delete(opt.get());
			idSuccess = true;
			if(sanPhamManageDTO.getLstHinhAnhMauSacDTO() != null) {
				for (HinhAnhMauSacDTO hinhAnhMauSacDTO : sanPhamManageDTO.getLstHinhAnhMauSacDTO()) {
					int countHinhAnh = sanPhamChiTietService.getCountSanPhamChiTietExistBySanPhamIdAndMauSacId(
							sanPhamManageDTO.getSanPhamId(), hinhAnhMauSacDTO.getMauSacAddImagesId());
					if (countHinhAnh == 0) {
						List<HinhAnh> lstHA = hinhAnhService.getHinhAnhBySanPhamIdAndMauSacId(
								sanPhamManageDTO.getSanPhamId(), hinhAnhMauSacDTO.getMauSacAddImagesId());
						for (HinhAnh hinhAnh : lstHA) {
							if (!hinhAnh.getTenAnh().isEmpty()) {
								storageService.delete(hinhAnh.getTenAnh());
								hinhAnhService.delete(hinhAnh);
							} else {
								idSuccess = false;
								redirect.addFlashAttribute("messageDanger",
										"Tên hình ảnh: '" + hinhAnh.getTenAnh() + "' không tồn tại");
							}
						}
					}
				}
			}
			int countSPCTExist = sanPhamChiTietService
					.getCountSanPhamChiTietExistBySanPhamId(sanPhamManageDTO.getSanPhamId());
			if (countSPCTExist == 0) {
				Optional<SanPham> optSP = sanPhamService.findById(sanPhamManageDTO.getSanPhamId());
				sanPhamService.delete(optSP.get());
			}
		} 
		redirect.addFlashAttribute("sanPhamManageDTO", sanPhamManageDTO);
		if(idSuccess == true) {
			redirect.addFlashAttribute("messageSuccess", "Xóa sản phẩm chi tiết thành công");
			return "redirect:/admin/product/edit/" + sanPhamManageDTO.getSanPhamId();
		}else {
			redirect.addFlashAttribute("messageDanger", "Xóa sản phẩm chi tiết thất bại");
			return "redirect:/admin/product/edit/" + sanPhamManageDTO.getSanPhamId();
		}
	}

	@PostMapping("updateOrCreateProductDetail")
	public String updateProductDetail(@Valid @ModelAttribute("sanPhamChiTietDTO") SanPhamChiTietDTO sanPhamChiTietDTO,
			BindingResult result, Model model) {
		Optional<SanPhamChiTiet> optSPCTOld = sanPhamChiTietService.findById(sanPhamChiTietDTO.getId());
		final String messageSuccess = "Cập nhật sản phẩm chi tiết thành công";
		final String messageDanger ="Cập nhật sản phẩm chi tiết thất bại";
		if(optSPCTOld.isPresent()) {
			if(optSPCTOld.get().getKichCo().getId().equals(sanPhamChiTietDTO.getKichCoId()) &&
				optSPCTOld.get().getMauSac().getId().equals(sanPhamChiTietDTO.getMauSacId()) &&
				optSPCTOld.get().getSanPham().getId().equals(sanPhamChiTietDTO.getSanPhamId()) ) {
				optSPCTOld.get().setCoHienThi(sanPhamChiTietDTO.getCoHienThi());
				optSPCTOld.get().setSoLuong(sanPhamChiTietDTO.getSoLuong());
				sanPhamChiTietService.save(optSPCTOld.get());
				model.addAttribute("messageSuccess", messageSuccess);
				model.addAttribute("sanPhamChiTietDTO", sanPhamChiTietDTO);
				return "admin/product/editProductDetail";
			}else {
				Optional<SanPhamChiTiet> optSPCT = sanPhamChiTietService.selectSanPhamChiTietDuplicate(
						sanPhamChiTietDTO.getMauSacId(), sanPhamChiTietDTO.getKichCoId(), sanPhamChiTietDTO.getSanPhamId());
				if (optSPCT.isPresent()) {
					sanPhamChiTietService.delete(optSPCTOld.get());
					optSPCT.get().setCoHienThi(sanPhamChiTietDTO.getCoHienThi());
					optSPCT.get().setSoLuong(sanPhamChiTietDTO.getSoLuong());

					Optional<KichCo> optKC = kichCoService.findById(sanPhamChiTietDTO.getKichCoId());
					if (optKC.isPresent()) {
						optSPCT.get().setKichCo(optKC.get());
					} else {
						model.addAttribute("messageDanger", messageSuccess);
						model.addAttribute("sanPhamChiTietDTO", sanPhamChiTietDTO);
						return "admin/product/editProductDetail";
					}

					Optional<MauSac> optMS = mauSacService.findById(sanPhamChiTietDTO.getMauSacId());
					if (optMS.isPresent()) {
						optSPCT.get().setMauSac(optMS.get());
					} else {
						model.addAttribute("messageDanger", messageDanger);
						model.addAttribute("sanPhamChiTietDTO", sanPhamChiTietDTO);
						return "admin/product/editProductDetail";
					}

					sanPhamChiTietService.save(optSPCT.get());
					model.addAttribute("messageSuccess", messageSuccess);
					model.addAttribute("sanPhamChiTietDTO", sanPhamChiTietDTO);
					return "admin/product/editProductDetail";
				} else {
					optSPCTOld.get().setCoHienThi(sanPhamChiTietDTO.getCoHienThi());
					optSPCTOld.get().setSoLuong(sanPhamChiTietDTO.getSoLuong());
					Optional<KichCo> optKC = kichCoService.findById(sanPhamChiTietDTO.getKichCoId());
					if (optKC.isPresent()) {
						optSPCTOld.get().setKichCo(optKC.get());
					} else {
						model.addAttribute("messageDanger", messageDanger);
						model.addAttribute("sanPhamChiTietDTO", sanPhamChiTietDTO);
						return "admin/product/editProductDetail";
					}

					Optional<MauSac> optMS = mauSacService.findById(sanPhamChiTietDTO.getMauSacId());
					if (optMS.isPresent()) {
						optSPCTOld.get().setMauSac(optMS.get());
					} else {
						model.addAttribute("messageDanger", messageDanger);
						model.addAttribute("sanPhamChiTietDTO", sanPhamChiTietDTO);
						return "admin/product/editProductDetail";
					}
					Optional<SanPham> optSP = sanPhamService.findById(sanPhamChiTietDTO.getSanPhamId());
					if (optSP.isPresent()) {
						optSPCTOld.get().setSanPham(optSP.get());
					} else {
						model.addAttribute("messageDanger", "Cập nhật sản phẩm chi tiết thất bại");
						model.addAttribute("sanPhamChiTietDTO", sanPhamChiTietDTO);
						return "admin/product/editProductDetail";
					}
					
					sanPhamChiTietService.save(optSPCTOld.get());
					
					model.addAttribute("messageSuccess", "Cập nhật sản phẩm chi tiết thành công");
					model.addAttribute("sanPhamChiTietDTO", sanPhamChiTietDTO);
					return "admin/product/editProductDetail";
				}
			}
		}else {
			model.addAttribute("sanPhamChiTietDTO", sanPhamChiTietDTO);
			model.addAttribute("messageDanger", "Cập nhật sản phẩm chi tiết thất bại");
			return "admin/product/editProductDetail";
		}
	}
	
	@GetMapping("exportExcelProduct")
	public String exportExcelProduct(@ModelAttribute("SPAndSPCTSearchDto") SPAndSPCTSearchDto dataSearch,
			Optional<Integer> page, Optional<Integer> size, final RedirectAttributes redirect){
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);
		redirect.addFlashAttribute("SPAndSPCTSearchDto", dataSearch);
		redirect.addFlashAttribute("page", currentPage);
		redirect.addFlashAttribute("size", pageSize);
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
		Page<SanPham> resultPage = null;
		Optional<SPAndSPCTSearchDto> optDataSearch = Optional.of(dataSearch);
		List<SanPhamProductManageDTO> lstDto = new ArrayList<>();
		if (optDataSearch.isPresent()) {
			resultPage = sanPhamService.searchProductExist(dataSearch, pageable);
			if(resultPage.hasContent()) {
				for (SanPham sanPham : resultPage.getContent()) {
					int countSPCT = sanPhamChiTietService.getCountSanPhamChiTietExistBySanPhamId(sanPham.getId());
					List<HinhAnh> lstHA = hinhAnhService.getHinhAnhChinhBySanPhamId(sanPham.getId());
					List<String> lstHAStr = new ArrayList<>();
					for (HinhAnh hinhAnh : lstHA) {
						lstHAStr.add(hinhAnh.getTenAnh());
					}
					SanPhamProductManageDTO spMN = new SanPhamProductManageDTO();
					spMN.setSanPham(sanPham);
					spMN.setAnhChinhs(lstHAStr);
					spMN.setTongSoLuong(countSPCT);
					lstDto.add(spMN);
				}
			}else {
				redirect.addFlashAttribute("messageDanger", "Không có dữ liệu để export");
				return "redirect:/admin/product";
			}
		}
		boolean checked = false;
		try {
			sanPhamChiTietService.WritingToExcelProduct(lstDto);
		} catch (IOException e) {
			checked = true;
			e.printStackTrace();
		}
		if(!checked) {
			redirect.addFlashAttribute("messageSuccess", "Export excel thành công");
		}else {
			redirect.addFlashAttribute("messageDanger", "Export excel thất bại");
		}
		return "redirect:/admin/product";
	}
	
	public void showViewBeforeSearch(ModelMap model, SPAndSPCTSearchDto dataSearch, Optional<Integer> page,
			Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);

		Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
		Page<SanPham> resultPage = null;
		Optional<SPAndSPCTSearchDto> optDataSearch = Optional.of(dataSearch);
		if (optDataSearch.isPresent()) {
			resultPage = sanPhamService.searchProductExist(dataSearch, pageable);
			model.addAttribute("dataSearch", dataSearch);
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
		model.addAttribute("sanPhamPage", resultPage);
	}
	
	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}

}
