package com.example.clothsdanawa.auth.dto;

import com.example.clothsdanawa.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthResponseDto {
	private Long id;

	@Builder
	private AuthResponseDto(Long id) {
		this.id = id;
	}

	public static AuthResponseDto from(User user) {
		return AuthResponseDto.builder()
			.id(user.getUser_id())
			.build();
	}
}
