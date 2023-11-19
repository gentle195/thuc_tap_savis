package com.fpoly.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.fpoly.dto.KhachHangDTO;
import com.fpoly.dto.TaiKhoanDTO;
import com.fpoly.entity.AuthenticationProvider;

import org.springframework.stereotype.Service;

@Service
public interface KhachHangService {

List<KhachHangDTO> findAllByTrangThaiCoPhanTrang(Integer trangThai,Pageable pageable);
	

	void capNhatTrangThaiThanhDangHoatDongTheoMa(long[] ids);

	void capNhatTrangThaiThanhKhongHoatDongTheoMa(long[] ids);

	KhachHangDTO findById(Long id);

	KhachHangDTO save(KhachHangDTO khachHangDTO);

	List<KhachHangDTO> findAll(Pageable pageable);
	
	List<KhachHangDTO> findAll();

	List<KhachHangDTO> findAllByInputCoPhanTrang(String soDienThoai,Pageable pageable);
	
	List<KhachHangDTO> findAllByInputVaTrangThaiCoPhanTrang(String soDienThoai,Integer trangThai,Pageable pageable);

	int countAll();

	int countByTrangThai(Integer trangThai);

	int countByInput(String input);

	int countByInputVaTrangThai(String input, Integer trangThai);


	void capNhatTrangThaiTheoId(Long id);


	void updateUserStatus(Long id, int trangThai);


	KhachHangDTO findByEmail(String auth);


	KhachHangDTO findByEmailAndTrangThai(String email, int i);


	KhachHangDTO register(KhachHangDTO khachHangDTO);


	String sendCode(String email);


	void updatePassword(String email,KhachHangDTO khachHangDTO);


	void capNhatMatKhau(TaiKhoanDTO taiKhoanDTO);

	public void taoMoiKhachHang(String email, String fullname ,AuthenticationProvider provider);

	public void capNhatKhachHang(String email, String fullname,AuthenticationProvider provider);
	
//
//List<KhachHangDTO> findAllByTrangThaiCoPhanTrang(Integer trangThai,Pageable pageable);
//	
//	List<KhachHangDTO> locDanhSachTheoTrangThai(Integer trangThai );
//
//	void capNhatTrangThaiThanhDangHoatDongTheoMa(long[] ids);
//
//	void capNhatTrangThaiThanhKhongHoatDongTheoMa(long[] ids);
//
//	KhachHangDTO findById(Long id);
//
//	KhachHangDTO save(KhachHangDTO khachHangDTO);
//
//	List<KhachHangDTO> findAll(Pageable pageable);
//	
//	List<KhachHangDTO> findAll();
//
//	List<KhachHangDTO> findAllBySoDienThoaiCoPhanTrang(String soDienThoai,Pageable pageable);
//	
//	List<KhachHangDTO> findAllBySoDienThoaiVaTrangThaiCoPhanTrang(String soDienThoai,Integer trangThai,Pageable pageable);
//
//	int countAll();
//
//	int countByTrangThai(Integer trangThai);
//
//	int countBySoDienThoai(String soDienThoai);
//
//	int countBySoDienThoaiVaTrangThai(String soDienThoai, Integer trangThai);

}
