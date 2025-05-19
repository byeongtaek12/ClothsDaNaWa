package com.example.clothsdanawa.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.clothsdanawa.user.dto.UserResponseDto;
import com.example.clothsdanawa.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public List<UserResponseDto> getUser() {

		return userRepository.findAll()
			.stream()
			.map(UserResponseDto::from)
			.toList();
	}

	public UserResponseDto getUserById(Long userId) {

		return UserResponseDto.from(userRepository.findByIdOrElseThrow(userId));

	}

}
