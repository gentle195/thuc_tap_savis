package com.fpoly.security.oauth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {

	private OAuth2User auth ;
	
	public CustomOAuth2User(OAuth2User auth) {
		this.auth = auth;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return auth.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
		return authorities ;
	}

	@Override
	public String getName() {
		return auth.getAttribute("email");
	}
	
	public String getFullname() {
		return auth.getAttribute("name");
	}
	
	public String getEmail() {
		return auth.getAttribute("email");
	}
	
}
