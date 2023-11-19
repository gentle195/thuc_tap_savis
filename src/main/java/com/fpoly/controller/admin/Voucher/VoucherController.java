package com.fpoly.controller.admin.Voucher;

import com.fpoly.dto.KhuyenMaiDTO;
import com.fpoly.service.KhuyenMaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class VoucherController {
    public static final String MSG_DANGER = "msgDanger";
    private static final String REDIRECT_GET_VOUCHER = "redirect:/admin/voucher";
    private static final String ADMIN_VOUCHER_INDEX = "admin/voucher/index";
    private static final String ADMIN_VOUCHER_EDIT = "admin/voucher/edit";
    public static final String MSG_SUCCESS = "msgSuccess";
    private final KhuyenMaiService khuyenMaiService;

    @GetMapping("/voucher")
    public String getVoucher(Model model,
                             @RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "size", defaultValue = "10") int size,
                             @RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "status", defaultValue = "ALL") String status,
                             @RequestParam(value = "discountStart", defaultValue = "0") String startStr,
                             @RequestParam(value = "dateFrom", required = false) String dateFromStr,
                             @RequestParam(value = "dateTo", required = false) String dateToStr,
                             @RequestParam(value = "discountEnd", defaultValue = "100") String endStr) {
        Integer start = Integer.parseInt(startStr);
        Integer end = Integer.parseInt(endStr);

        Page<KhuyenMaiDTO> list = khuyenMaiService.getListKhuyenMai(page, size, keyword, status, start, end, dateFromStr, dateToStr);

        model.addAttribute("vouchers", list);
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("size", size);
        model.addAttribute("dateFrom", dateFromStr);
        model.addAttribute("dateTo", dateToStr);
        int totalPages = list.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return ADMIN_VOUCHER_INDEX;
    }

    @GetMapping("/voucher/create")
    public String createVoucherForm(Model model) {
        model.addAttribute("voucher", new KhuyenMaiDTO());
        return ADMIN_VOUCHER_EDIT;
    }

    @PostMapping("/voucher/create")
    public String handleCreate(@ModelAttribute("voucher") @Valid KhuyenMaiDTO dto,
                               BindingResult result,
                               RedirectAttributes ra,
                               Model model) {
        if (khuyenMaiService.checkExistVoucher(dto)) {
            result.rejectValue("tenKhuyenMai", "Exist", "Đã tồn tại mã khuyến mãi này!");
        }

        if (result.hasErrors()) {
            return ADMIN_VOUCHER_EDIT;
        }
        khuyenMaiService.createVoucher(dto);
        ra.addFlashAttribute(MSG_SUCCESS, "Tạo voucher thành công");

        return REDIRECT_GET_VOUCHER;
    }

    @GetMapping("/voucher/edit/{id}")
    public String editVoucher(Model model, @PathVariable Long id) {
        if (!model.containsAttribute("voucher")) {
            model.addAttribute("voucher", khuyenMaiService.getVoucher(id));
        }

        return ADMIN_VOUCHER_EDIT;
    }

    @PostMapping("/voucher/edit/{id}")
    public String handleEdit(@PathVariable Long id,
                             @ModelAttribute("voucher") @Valid KhuyenMaiDTO dto,
                             BindingResult result,
                             RedirectAttributes ra,
                             Model model) {
        KhuyenMaiDTO entity = khuyenMaiService.getVoucher(id);
        if (!entity.getTenKhuyenMai().equals(dto.getTenKhuyenMai()) && (khuyenMaiService.checkExistVoucher(dto))) {
            result.rejectValue("tenKhuyenMai", "Exist", "Đã tồn tại mã khuyến mãi này!");
        }

        if (result.hasErrors()) {
            ra.addFlashAttribute("org.springframework.validation.BindingResult.voucher", result);
            ra.addFlashAttribute("voucher", dto);
            return "redirect:/admin/voucher/edit/" + id;
        }

        khuyenMaiService.editVoucher(id, dto);
        ra.addFlashAttribute(MSG_SUCCESS, "Sửa voucher thành công");
        return REDIRECT_GET_VOUCHER;
    }

    @GetMapping("/voucher/delete/{id}")
    public String deleteVoucher(@PathVariable Long id,
                                RedirectAttributes ra) {
        if (khuyenMaiService.deleteVoucher(id))
            ra.addFlashAttribute(MSG_SUCCESS, "Xoá voucher thành công");
        else
            ra.addFlashAttribute(MSG_DANGER, "Xoá voucher thất bại");

        return REDIRECT_GET_VOUCHER;
    }

    @GetMapping("/voucher/toggle/{id}")

    public String disableVoucher(@PathVariable Long id,
                                 RedirectAttributes ra
                                 ) {
        if (khuyenMaiService.toggleDisableVoucher(id))
            ra.addFlashAttribute(MSG_SUCCESS, "Sửa trạng thái voucher thành công");
        else
            ra.addFlashAttribute(MSG_DANGER, "Sửa trạng thái voucher thất bại");
        return REDIRECT_GET_VOUCHER;
    }
}
