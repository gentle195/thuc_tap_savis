package com.fpoly.security.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fpoly.entity.GioHang;
import com.fpoly.entity.KhachHang;
import com.fpoly.repository.GioHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fpoly.dto.NguoiDungDTO;
import com.fpoly.entity.AuthenticationProvider;
import com.fpoly.entity.NguoiDung;
import com.fpoly.repository.KhachHangRepository;
import com.fpoly.service.KhachHangService;
import com.fpoly.service.NguoiDungService;
import com.fpoly.service.impl.NguoiDungDetailsService;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private GioHangRepository gioHangRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        String fullname = oAuth2User.getFullname();
        NguoiDung nguoiDung = nguoiDungService.findByEmail(email);
        KhachHang khachHang = khachHangRepository.findByEmail(email);

        if (nguoiDung == null) {
            nguoiDungService.taoNguoiDungSauKhiDangNhapVoiMangXaHoiThanhCong(email, fullname, AuthenticationProvider.GOOGLE);
            khachHangService.taoMoiKhachHang(email, fullname, AuthenticationProvider.GOOGLE);
        } else {
            nguoiDungService.capNhatNguoiDungSauKhiDangNhapVoiMangXaHoiThanhCong(email, fullname, AuthenticationProvider.GOOGLE);
            khachHangService.capNhatKhachHang(email, fullname, AuthenticationProvider.GOOGLE);
        }

        redirectStrategy.sendRedirect(request, response, "/customer/home");
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
