package com.example.clothsdanawa.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.clothsdanawa.auth.dto.AuthLoginRequestDto;
import com.example.clothsdanawa.auth.dto.AuthLoginResponseDto;
import com.example.clothsdanawa.auth.dto.AuthSignUpRequestDto;
import com.example.clothsdanawa.auth.dto.AuthSignupResponseDto;
import com.example.clothsdanawa.auth.service.AuthService;
import com.example.clothsdanawa.common.security.CustomUserPrincipal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<AuthSignupResponseDto> signup(@Valid @RequestBody AuthSignUpRequestDto authSignUpRequestDto) {

		return ResponseEntity.status(201).body(authService.signup(authSignUpRequestDto));

	}

	@PostMapping("/login")
	public ResponseEntity<AuthLoginResponseDto> login(@RequestBody AuthLoginRequestDto authLoginRequestDto,
		HttpServletResponse response) {

		return ResponseEntity.status(200).body(authService.login(authLoginRequestDto, response));

	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(
		@AuthenticationPrincipal CustomUserPrincipal customUserPrincipal, HttpServletRequest request,
		HttpServletResponse response) {
		authService.logout(customUserPrincipal, request, response);
		return ResponseEntity.status(200).build();
	}

	@PostMapping("/refreshToken")
	public ResponseEntity<AuthLoginResponseDto> reissueToken(HttpServletRequest request) {
		return ResponseEntity.status(200).body(authService.reissueToken(request));
	}

}
