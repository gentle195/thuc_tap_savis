package com.fpoly.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fpoly.entity.NguoiDung;
import com.fpoly.repository.NguoiDungRepository;
import com.fpoly.security.NguoiDungDetails;

@Service
public class NguoiDungDetailsService implements UserDetailsService {
	
	@Autowired
	private NguoiDungRepository nguoiDungRepository ;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		NguoiDung nguoiDung = nguoiDungRepository.findByEmailAndTrangThaiAndDaXoa(email);
		if(nguoiDung == null) {
			throw new UsernameNotFoundException("Không tìm thấy người dùng với email này !");
		}
		return new NguoiDungDetails(nguoiDung);
	}
	
}
