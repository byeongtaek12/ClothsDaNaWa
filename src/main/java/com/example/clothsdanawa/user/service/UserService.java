package com.example.clothsdanawa.user.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.clothsdanawa.common.exception.BaseException;
import com.example.clothsdanawa.common.exception.ErrorCode;
import com.example.clothsdanawa.common.security.CustomUserPrincipal;
import com.example.clothsdanawa.user.dto.UserResponseDto;
import com.example.clothsdanawa.user.dto.UserUpdateRequestDto;
import com.example.clothsdanawa.user.dto.UserUpdateResponseDto;
import com.example.clothsdanawa.user.entity.User;
import com.example.clothsdanawa.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public List<UserResponseDto> getUser() {

		return userRepository.findAllByDeletedAtIsNull()
			.stream()
			.map(UserResponseDto::from)
			.toList();
	}

	public UserResponseDto getUserById(Long userId) {

		return UserResponseDto.from(userRepository.findByUserIdAndDeletedAtIsNullOrElseThrow(userId));

	}

	@Transactional
	public UserUpdateResponseDto updateUser(CustomUserPrincipal customUserPrincipal,
		Long userId, UserUpdateRequestDto userUpdateRequestDto) {

		User findedUser = userRepository.findByUserIdAndDeletedAtIsNullOrElseThrow(customUserPrincipal.getUserId());

		if (userId != findedUser.getUserId()) {
			throw new BaseException(ErrorCode.FORBIDDEN_NOT_MINE);
		}

		if (userUpdateRequestDto.getPassword() != null) {
			if (findedUser.getPassword() == null) {
				throw new BaseException(ErrorCode.FORBIDDEN_NOT_CHANGE);
			}
			String encodedPassword = passwordEncoder.encode(userUpdateRequestDto.getPassword());

			findedUser.updateUser(userUpdateRequestDto, encodedPassword);
			return UserUpdateResponseDto.of(findedUser, userUpdateRequestDto.getPassword());
		}
		findedUser.updateUser(userUpdateRequestDto, null);
		return UserUpdateResponseDto.of(findedUser, userUpdateRequestDto.getPassword());
	}

	@Transactional
	public void deleteUser(CustomUserPrincipal customUserPrincipal, Long userId) {
		User findedUser = userRepository.findByUserIdAndDeletedAtIsNullOrElseThrow(customUserPrincipal.getUserId());

		if (userId != findedUser.getUserId()) {
			throw new BaseException(ErrorCode.FORBIDDEN_NOT_MINE);
		}
		findedUser.softDelete();
	}
}
