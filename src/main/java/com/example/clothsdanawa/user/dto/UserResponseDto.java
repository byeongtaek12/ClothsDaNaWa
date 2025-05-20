package com.example.clothsdanawa.user.dto;

import com.example.clothsdanawa.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserResponseDto {
	private Long id;
	private String name;
	private String email;

	private UserResponseDto(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public static UserResponseDto from(User user) {
		return UserResponseDto.builder()
			.id(user.getUserId())
			.name(user.getName())
			.email(user.getEmail())
			.build();
	}
}
