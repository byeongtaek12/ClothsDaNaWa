package com.example.clothsdanawa.user.entity;

import com.example.clothsdanawa.auth.dto.AuthLoginRequestDto;
import com.example.clothsdanawa.auth.dto.AuthSignUpRequestDto;
import com.example.clothsdanawa.common.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long user_id;

	private String name;

	private String email;

	private String password;

	private String address;

	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Builder
	private User(String name, String email, String password, String address, UserRole role) {
		this.name=name;
		this.email=email;
		this.password=password;
		this.address=address;
		this.role=role;
	}

	public static User from(AuthSignUpRequestDto authSignUpRequestDto) {
		return User.builder()
			.name(authSignUpRequestDto.getName())
			.email(authSignUpRequestDto.getEmail())
			.password(authSignUpRequestDto.getPassword())
			.address(authSignUpRequestDto.getAddress())
			.role(UserRole.valueOf(authSignUpRequestDto.getUserRole()))
			.build();
	}
}
