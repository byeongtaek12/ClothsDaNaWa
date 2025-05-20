package com.example.clothsdanawa.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.clothsdanawa.user.dto.UserResponseDto;
import com.example.clothsdanawa.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping
	public ResponseEntity<List<UserResponseDto>> getUser() {

		return ResponseEntity.status(200).body(userService.getUser());

	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {

		return ResponseEntity.status(200).body(userService.getUserById(userId));

	}

	// @PatchMapping("/{userId}")
	// public ResponseEntity<UserUpdateResponseDto> updateUser(
	// 	@AuthenticationPrincipal CustomUserPrincipal customUserPrincipal,
	// 	@PathVariable Long userId, @RequestBody UserUpdateRequestDto userUpdateRequestDto
	// ) {
	// 	return ResponseEntity.status(200).body(userService.updateUser(
	// 		customUserPrincipal, userId, userUpdateRequestDto));
	//
	// }

}
