package com.example.clothsdanawa.user.controller;

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

	@GetMapping("/{userId}")
	public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {

		return ResponseEntity.status(200).body(userService.getUserById(userId));

	}

}
