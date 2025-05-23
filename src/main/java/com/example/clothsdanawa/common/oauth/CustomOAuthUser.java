package com.example.clothsdanawa.common.oauth;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import com.example.clothsdanawa.user.entity.User;

public class CustomOAuthUser extends DefaultOAuth2User {

	private final User user;

	public CustomOAuthUser(User user, Map<String, Object> attributes) {
		super(List.of(new SimpleGrantedAuthority(user.getUserRole().toString())), attributes,
			"email");
		this.user = user;
	}

	public Long getUserId() {
		return user.getUserId();
	}

}
