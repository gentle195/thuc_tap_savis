package com.fpoly.api.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fpoly.service.GioHangService;

@RestController(value = "gioHangAPI")
public class GioHangAPI {
	
	@Autowired
	private GioHangService gioHangService ;
	
	@DeleteMapping("/customer/api/gio-hang/tinh-tien")
	public int tinhTienGioHang(@RequestBody  long[] idGioHangChiTiet) {
			return gioHangService.tinhTienGioHangTheoMaGioHangChiTiet(idGioHangChiTiet);
	}
}
