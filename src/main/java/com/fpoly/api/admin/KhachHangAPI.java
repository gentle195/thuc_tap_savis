package com.fpoly.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.dto.KhachHangDTO;
import com.fpoly.service.KhachHangService;

@RestController(value = "khachHangAPIOfAdmin")
public class KhachHangAPI {
    @Autowired
    private KhachHangService KhachHangService;

    @PostMapping("/admin/api/cap-nhat-trang-thai")
    public ResponseEntity<String> updateStatus(@RequestParam("userId") Long id, @RequestParam("status") int trangThai) {
        try {
            KhachHangService.updateUserStatus(id, trangThai);
            return ResponseEntity.ok("Cập nhật trạng thái thành công");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Khách hàng không tồn tại");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi cập nhật trạng thái");
        }
    }

    @GetMapping("/admin/api/khach-hang")
    public List<KhachHangDTO> layDanhSachKhachHang() {
        return KhachHangService.findAll();
    }

    @PutMapping("/admin/api/khach-hang/trang-thai-dang-hoat-dong")
    public void deleteKhachHangByTrangThaiDangHoatDong(@RequestBody long[] ids) {
        KhachHangService.capNhatTrangThaiThanhDangHoatDongTheoMa(ids);
    }

    @PutMapping("/admin/api/khach-hang/trang-thai-khong-hoat-dong")
    public void deleteKhachHangByTrangThaiKhongHoatDong(@RequestBody long[] ids) {
        KhachHangService.capNhatTrangThaiThanhKhongHoatDongTheoMa(ids);
    }

    @PostMapping("/admin/api/khach-hang")
    public KhachHangDTO themMoiKhachHang(@RequestBody KhachHangDTO khachHangDTO) {
        return KhachHangService.save(khachHangDTO);
    }

    @PutMapping("/admin/api/khach-hang")
    public KhachHangDTO capNhatKhachHang(@RequestBody KhachHangDTO khachHangDTO) {
        return KhachHangService.save(khachHangDTO);
    }
//	@Autowired
//	private KhachHangService KhachHangService ;
//	
//	
//	@GetMapping("/admin/api/khach-hang")
//	public List<KhachHangDTO> layDanhSachKhachHang(){
//		return KhachHangService.findAll();
//	}
//	
//	@DeleteMapping("/admin/api/khach-hang/trang-thai-dang-hoat-dong")
//	public void deleteKhachHangByTrangThaiDangHoatDong(@RequestBody long[] ids) {
//			KhachHangService.capNhatTrangThaiThanhDangHoatDongTheoMa(ids);
//	}
//	
//	
//	@DeleteMapping("/admin/api/khach-hang/trang-thai-khong-hoat-dong")
//	public void deleteKhachHangByTrangThaiKhongHoatDong(@RequestBody long[] ids) {
//			KhachHangService.capNhatTrangThaiThanhKhongHoatDongTheoMa(ids);
//	}
//	
//	@PostMapping("/admin/api/khach-hang")
//	public KhachHangDTO themMoiKhachHang(@RequestBody KhachHangDTO khachHangDTO) {
//		return KhachHangService.save(khachHangDTO);
//	}
//	@PutMapping("/admin/api/khach-hang")
//	public KhachHangDTO capNhatKhachHang(@RequestBody KhachHangDTO khachHangDTO) {
//		return KhachHangService.save(khachHangDTO);
//	}
}
