package com.example.clothsdanawa.user.entity;

import com.example.clothsdanawa.auth.dto.AuthSignUpRequestDto;
import com.example.clothsdanawa.common.BaseEntity;
import com.example.clothsdanawa.user.dto.UserUpdateRequestDto;

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
	private Long userId;

	private String name;

	private String email;

	private String password;

	private String address;

	@Enumerated(EnumType.STRING)
	private UserRole userRole;

	@Builder
	private User(String name, String email, String password, String address, UserRole userRole) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.address = address;
		this.userRole = userRole;
	}

	public static User of(AuthSignUpRequestDto authSignUpRequestDto, String encodedPassword) {
		return User.builder()
			.name(authSignUpRequestDto.getName())
			.email(authSignUpRequestDto.getEmail())
			.password(encodedPassword)
			.address(authSignUpRequestDto.getAddress())
			.userRole(com.example.clothsdanawa.user.entity.UserRole.valueOf(authSignUpRequestDto.getUserRole()))
			.build();
	}

	public void updateUser(UserUpdateRequestDto userUpdateRequestDto) {
		if (userUpdateRequestDto.getName() != null) {
			this.name = userUpdateRequestDto.getName();
		}

		if (userUpdateRequestDto.getEmail() != null) {
			this.email = userUpdateRequestDto.getEmail();
		}

		if (userUpdateRequestDto.getPassword() != null) {
			this.password = userUpdateRequestDto.getPassword();
		}

		if (userUpdateRequestDto.getAddress() != null) {
			this.address = userUpdateRequestDto.getAddress();
		}
	}
}
