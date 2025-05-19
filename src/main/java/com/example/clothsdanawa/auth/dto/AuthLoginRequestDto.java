package com.example.clothsdanawa.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthLoginRequestDto {
	private String email;
	private String password;

}
