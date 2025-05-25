package com.example.clothsdanawa.auth.service;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.clothsdanawa.auth.dto.AuthLoginRequestDto;
import com.example.clothsdanawa.auth.dto.AuthLoginResponseDto;
import com.example.clothsdanawa.auth.dto.AuthSignUpRequestDto;
import com.example.clothsdanawa.auth.dto.AuthSignupResponseDto;
import com.example.clothsdanawa.common.exception.BaseException;
import com.example.clothsdanawa.common.exception.ErrorCode;
import com.example.clothsdanawa.common.jwt.JwtUtil;
import com.example.clothsdanawa.common.security.CustomUserPrincipal;
import com.example.clothsdanawa.user.entity.User;
import com.example.clothsdanawa.user.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	@Transactional
	public AuthSignupResponseDto signup(AuthSignUpRequestDto authSignUpRequestDto) {

		if (userRepository.existsByEmail(authSignUpRequestDto.getEmail())) {
			throw new BaseException(ErrorCode.CONFLICT_EMAIL);
		}

		String encodedPassword = passwordEncoder.encode(authSignUpRequestDto.getPassword());

		User user = User.of(authSignUpRequestDto, encodedPassword);

		User savedUser = userRepository.save(user);

		return AuthSignupResponseDto.of(savedUser);
	}

	@Transactional
	public AuthLoginResponseDto login(AuthLoginRequestDto authLoginRequestDto, HttpServletResponse response) {

		User findedUser = userRepository.findByEmailAndDeletedAtIsNullOrElseThrow(authLoginRequestDto.getEmail());
		if (!passwordEncoder.matches(authLoginRequestDto.getPassword(), findedUser.getPassword())) {
			throw new BaseException(ErrorCode.BAD_REQUEST_PASSWORD);
		}

		String jwtToken = jwtUtil.createToken(findedUser.getUserId(), findedUser.getName(), findedUser.getEmail(),
			findedUser.getUserRole());
		String refreshToken = jwtUtil.createRefreshToken(findedUser.getUserId());
		ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
			.httpOnly(true)
			.secure(true)
			.sameSite("Strict")
			.maxAge(TimeUnit.DAYS.toSeconds(7))
			.build();
		findedUser.updateRefreshToken(refreshToken);
		response.addHeader("Set-Cookie", cookie.toString());

		return AuthLoginResponseDto.of(findedUser, jwtToken);
	}

	@Transactional
	public void logout(CustomUserPrincipal customUserPrincipal, HttpServletRequest request,
		HttpServletResponse response) {

		String refreshToken = Arrays.stream(request.getCookies())
			.filter(a -> "refreshToken".equals(a.getName()))
			.map(Cookie::getValue)
			.findFirst()
			.orElse(null);

		User authUser = vaildRefreshToken(customUserPrincipal, refreshToken);

		authUser.updateRefreshToken("null");

		ResponseCookie cookie = ResponseCookie.from("refreshToken", null)
			.maxAge(0)
			.build();
		response.addHeader("Set-Cookie", cookie.toString());

	}

	public AuthLoginResponseDto reissueToken(HttpServletRequest request) {

		String refreshToken = Arrays.stream(request.getCookies())
			.filter(a -> a.getName().equals("refreshToken"))
			.map(Cookie::getValue)
			.findFirst().orElse(null);
		User vaildedUser = vaildRefreshToken1(refreshToken);

		String token = jwtUtil.createToken(vaildedUser.getUserId(), vaildedUser.getName(), vaildedUser.getEmail(),
			vaildedUser.getUserRole());

		return AuthLoginResponseDto.of(vaildedUser, token);
	}

	private User vaildRefreshToken(CustomUserPrincipal customUserPrincipal, String refreshToken) {
		User authUser = userRepository.findByUserIdAndDeletedAtIsNullOrElseThrow(customUserPrincipal.getUserId());
		if (authUser.getRefreshToken() == null || refreshToken == null) {
			throw new BaseException(ErrorCode.UNAUTHORIZED_NULL_REFRESH_TOKEN);
		}
		if (!authUser.getRefreshToken().equals(refreshToken)) {
			throw new BaseException(ErrorCode.FORBIDDEN_BUT_LOGIN_OK);

		}
		if (jwtUtil.isTokenExpired(refreshToken)) {
			throw new BaseException(ErrorCode.UNAUTHORIZED_REFRESH_TOKEN);
		}
		return authUser;
	}

	private User vaildRefreshToken1(String refreshToken) {

		Long userId = Long.valueOf(jwtUtil.extractClaims1(refreshToken).getSubject());

		User findedUser = userRepository.findByUserIdAndDeletedAtIsNullOrElseThrow(userId);

		if (findedUser.getRefreshToken() == null || refreshToken == null) {
			throw new BaseException(ErrorCode.UNAUTHORIZED_NULL_REFRESH_TOKEN);
		}
		if (!findedUser.getRefreshToken().equals(refreshToken)) {
			throw new BaseException(ErrorCode.FORBIDDEN_BUT_LOGIN_OK);

		}
		if (jwtUtil.isTokenExpired(refreshToken)) {
			throw new BaseException(ErrorCode.UNAUTHORIZED_REFRESH_TOKEN);
		}
		return findedUser;
	}
}
