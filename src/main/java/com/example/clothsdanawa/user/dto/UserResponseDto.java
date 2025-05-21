package com.example.clothsdanawa.user.dto;

import com.example.clothsdanawa.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {
	private Long id;
	private String name;
	private String email;

	public static UserResponseDto from(User user) {
		return UserResponseDto.builder()
				.id(user.getUserId())
				.name(user.getName())
				.email(user.getEmail())
				.build();
	}
}