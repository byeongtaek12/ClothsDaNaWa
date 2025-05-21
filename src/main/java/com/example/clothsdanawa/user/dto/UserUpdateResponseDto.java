package com.example.clothsdanawa.user.dto;

import java.time.LocalDateTime;

import com.example.clothsdanawa.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateResponseDto {
	private Long id;
	private String name;
	private String email;
	private String password;
	private String address;
	private String userRole;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public static UserUpdateResponseDto from(User user) {
		return UserUpdateResponseDto.builder()
			.id(user.getUserId())
			.name(user.getName())
			.email(user.getEmail())
			.password(user.getPassword())
			.address(user.getAddress())
			.userRole(user.getUserRole().toString())
			.createdAt(user.getCreatedAt())
			.updatedAt(user.getUpdatedAt())
			.build();
	}
}
