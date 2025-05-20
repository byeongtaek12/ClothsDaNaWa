package com.example.clothsdanawa.user.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateResponseDto {
	private Long id;
	private String name;
	private String email;
	private String password;
	private String address;
	private String userRole;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	private UserUpdateResponseDto(String name, String email, String password, String address, String userRole,
		LocalDateTime createdAt, LocalDateTime updatedAt) {

		this.name = name;
		this.email = email;
		this.password = password;
		this.address = address;
		this.userRole = userRole;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// public static UserUpdateResponseDto
}
