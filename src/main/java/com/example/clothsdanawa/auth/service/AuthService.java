package com.example.clothsdanawa.auth.service;

import com.example.clothsdanawa.common.jwt.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.clothsdanawa.auth.dto.AuthLoginRequestDto;
import com.example.clothsdanawa.auth.dto.AuthResponseDto;
import com.example.clothsdanawa.auth.dto.AuthSignUpRequestDto;
import com.example.clothsdanawa.user.entity.User;
import com.example.clothsdanawa.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public AuthResponseDto signup(AuthSignUpRequestDto authSignUpRequestDto) {

		if (userRepository.existsByEmail(authSignUpRequestDto.getEmail())) {
			throw new RuntimeException("이미 존재하는 이메일입니다");
		}

		String encodedPassword = passwordEncoder.encode(authSignUpRequestDto.getPassword());

		User user = User.of(authSignUpRequestDto, encodedPassword);

		User savedUser = userRepository.save(user);

		String jwtToken = jwtUtil.createToken(savedUser.getUserId(), savedUser.getName(), savedUser.getEmail(),
			savedUser.getUserRole());

		return AuthResponseDto.of(savedUser, jwtToken);
	}

	public AuthResponseDto login(AuthLoginRequestDto authLoginRequestDto) {

		User findedUser = userRepository.findByEmailOrElseThrow(authLoginRequestDto.getEmail());
		if (!passwordEncoder.matches(authLoginRequestDto.getPassword(), findedUser.getPassword())) {
			throw new RuntimeException("비밀번호가 틀렸습니다");
		}

		String jwtToken = jwtUtil.createToken(findedUser.getUserId(), findedUser.getName(), findedUser.getEmail(),
			findedUser.getUserRole());

		return AuthResponseDto.of(findedUser, jwtToken);
	}
}
