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

	public static UserUpdateResponseDto of(User user, String requestPassword) {
		return UserUpdateResponseDto.builder()
			.id(user.getUserId())
			.name(user.getName())
			.email(user.getEmail() + " 변경하셨다면 재로그인시 서비스이용가능합니다")
			.password(requestPassword != null ? requestPassword : "변동없습니다")
			.address(user.getAddress())
			.userRole(user.getUserRole().toString())
			.createdAt(user.getCreatedAt())
			.updatedAt(user.getUpdatedAt())
			.build();
	}
}
