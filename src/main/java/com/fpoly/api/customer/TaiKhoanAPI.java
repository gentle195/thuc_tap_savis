package com.fpoly.api.customer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.dto.DiaChiDTO;
import com.fpoly.dto.TaiKhoanDTO;
import com.fpoly.entity.DiaChi;
import com.fpoly.entity.KhachHang;
import com.fpoly.repository.DiaChiRepository;
import com.fpoly.repository.KhachHangRepository;
import com.fpoly.service.DiaChiService;
import com.fpoly.service.KhachHangService;
import com.fpoly.service.NguoiDungService;

@RestController(value = "taiKhoanAPI")
public class TaiKhoanAPI {
	
	@Autowired
	private KhachHangService khachHangService ;
	
	@Autowired
	private NguoiDungService nguoiDungService ;
	
	@Autowired
	private DiaChiService diaChiService ;
	
	@PutMapping("/customer/api/tai-khoan/doi-mat-khau")
	public ResponseEntity<String> doiMatKhauTaiKhoan(@RequestBody TaiKhoanDTO taiKhoanDTO) {
		if(taiKhoanDTO.getPassword().equals(taiKhoanDTO.getConfirm_password())){
			khachHangService.capNhatMatKhau(taiKhoanDTO);
			nguoiDungService.capNhatMatKhau(taiKhoanDTO);
			return ResponseEntity.ok("Đổi mật khẩu thành công !");
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Mật khẩu không khớp !");
		}
	}
	@PostMapping("/customer/api/dia-chi/khach-hang-dang-nhap")
	public DiaChiDTO themMoiDiaChiKhachHangDangNhap(@RequestBody DiaChiDTO diaChiDTO) {
		return diaChiService.save(diaChiDTO);
	}
	@DeleteMapping("/customer/api/dia-chi")
	public void xoaDiaChi(@RequestBody long[] ids) {
		diaChiService.delete(ids);
	}
	
	@Autowired
	private KhachHangRepository khachHangRepository ;
	
	@Autowired
	private DiaChiRepository diaChiRepository ;
	
	@PostMapping("/customer/api/update-dia-chi-mac-dinh")
    public @ResponseBody Map<String, Object> updateDiaChiMacDinh(@RequestParam("DiaChiID") long DiaChiID,
                                                                 @RequestParam("KhachHangID") long KhachHangID) {
        Map<String, Object> response = new HashMap<>();
        Optional<DiaChi> optionalDiaChi = diaChiRepository.findById(DiaChiID);
        Optional<KhachHang> optionalKhachHang = khachHangRepository.findById(KhachHangID);
        if (optionalDiaChi.isPresent() && optionalKhachHang.isPresent()) {
            DiaChi diaChi = optionalDiaChi.get();
            KhachHang khachHang = optionalKhachHang.get();

            for (DiaChi existingDiaChi : khachHang.getListDiaChi()) {
                if (existingDiaChi.getId() == diaChi.getId()) {
                    existingDiaChi.setLaDiaChiMacDinh(true);
                } else {
                    existingDiaChi.setLaDiaChiMacDinh(false);
                }
                diaChiRepository.save(existingDiaChi);
            }

            khachHang.setDiaChi(diaChi);
            khachHangRepository.save(khachHang);

        } else {
            response.put("success", false);
            response.put("error", "Lỗi");
        }

        response.put("success", true);


        return response;
    }
	
}
