package com.example.clothsdanawa.auth.dto;

import com.example.clothsdanawa.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthResponseDto {
	private Long id;
	private String jwtToken;

	@Builder
	private AuthResponseDto(Long id, String jwtToken) {
		this.id = id;
		this.jwtToken = jwtToken;
	}

	public static AuthResponseDto of(User user, String jwtToken) {
		return AuthResponseDto.builder()
			.id(user.getUserId())
			.jwtToken(jwtToken)
			.build();
	}
}
