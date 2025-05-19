package com.example.clothsdanawa.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.clothsdanawa.auth.dto.AuthResponseDto;
import com.example.clothsdanawa.auth.dto.AuthSignUpRequestDto;
import com.example.clothsdanawa.common.jwt.JwtUtil;
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
}
