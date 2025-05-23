package com.example.clothsdanawa.auth.dto;

import com.example.clothsdanawa.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthSignupResponseDto {
	private Long id;

	public static AuthSignupResponseDto of(User user) {
		return AuthSignupResponseDto.builder()
			.id(user.getUserId())
			.build();
	}
}
