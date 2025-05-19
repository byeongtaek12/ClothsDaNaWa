package com.example.clothsdanawa.auth.service;

import org.springframework.stereotype.Service;

import com.example.clothsdanawa.auth.dto.AuthResponseDto;
import com.example.clothsdanawa.auth.dto.AuthSignUpRequestDto;
import com.example.clothsdanawa.user.entity.User;
import com.example.clothsdanawa.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;

	public AuthResponseDto signup(AuthSignUpRequestDto authSignUpRequestDto) {

		User user = User.from(authSignUpRequestDto);

		User savedUser = userRepository.save(user);

		return AuthResponseDto.from(savedUser);
	}
}
