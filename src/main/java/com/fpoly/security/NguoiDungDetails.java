package com.fpoly.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fpoly.entity.NguoiDung;
import com.fpoly.entity.NguoiDungVaiTro;
import org.springframework.security.oauth2.core.user.OAuth2User;

@SuppressWarnings("serial")
public class NguoiDungDetails implements UserDetails {

    private NguoiDung nguoiDung;

    public NguoiDungDetails(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung; 
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<NguoiDungVaiTro> nguoiDungVaiTros = this.nguoiDung.getListNguoiDungVaiTro();
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        for (NguoiDungVaiTro nguoiDungVaiTro : nguoiDungVaiTros) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + nguoiDungVaiTro.getVaiTro().getCode()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.nguoiDung.getMatKhau();
    }

    @Override
    public String getUsername() {
        return this.nguoiDung.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFullname() {
        return nguoiDung.getTenNguoiDung();
    }

    public String getNumberPhone() {
        return nguoiDung.getSoDienThoai();
    }

    public String getMaNguoiDung() {
        return nguoiDung.getMaNguoiDung();
    }

    public long getId() {
        return nguoiDung.getId();
    }

    public String getAnhNguoiDung() {
    	if(nguoiDung.getAnhNhanVien() != null) {
    		return nguoiDung.getAnhNhanVien();
    	}else {
    		return null ;
    	}
    }
}