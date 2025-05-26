package com.example.clothsdanawa.common.oauth;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.example.clothsdanawa.common.exception.BaseException;
import com.example.clothsdanawa.common.exception.ErrorCode;
import com.example.clothsdanawa.common.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomFailureHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(401);

		if (exception instanceof OAuth2AuthenticationException e) {
			if (e.getError().getErrorCode().equals(new BaseException(ErrorCode.CONFLICT_EMAIL).getMessage())) {
				response.getWriter().write(new ObjectMapper().writeValueAsString(
					ErrorResponse.from(ErrorCode.CONFLICT_EMAIL)));
				return;
			} else if (e.getError()
				.getErrorCode()
				.equals(new BaseException(ErrorCode.BAD_REQUEST_EMAIL_ESSENTIAL).getMessage())) {
				response.getWriter().write(new ObjectMapper().writeValueAsString(
					ErrorResponse.from(ErrorCode.BAD_REQUEST_EMAIL_ESSENTIAL)));
				return;
			}
			response.getWriter().write(new ObjectMapper().writeValueAsString(
				ErrorResponse.from(ErrorCode.UNAUTHORIZED_LOGIN_FAILED)));
		}
	}
}
