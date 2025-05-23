package com.example.clothsdanawa.common.oauth;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.clothsdanawa.common.exception.ErrorCode;
import com.example.clothsdanawa.common.exception.ErrorResponse;
import com.example.clothsdanawa.common.jwt.JwtUtil;
import com.example.clothsdanawa.user.entity.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
	private final JwtUtil jwtUtil;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		CustomOAuthUser principal = (CustomOAuthUser)authentication.getPrincipal();
		List<String> roles = principal.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.toList();
		String createdToken = jwtUtil.createToken(principal.getUserId(), principal.getName(),
			principal.getAttribute("email"), UserRole.from(roles.get(0)));
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(200);
		response.getWriter().write(new ObjectMapper().writeValueAsString(
			ErrorResponse.from(ErrorCode.UNAUTHORIZED_LOGIN_SUCCESS))
		);
		response.addHeader("Authorization", createdToken);
	}
}
