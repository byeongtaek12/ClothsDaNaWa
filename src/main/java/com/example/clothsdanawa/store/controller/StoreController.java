package com.example.clothsdanawa.store.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.clothsdanawa.common.security.CustomUserPrincipal;
import com.example.clothsdanawa.redis.RedisService;
import com.example.clothsdanawa.store.dto.request.StoreCreateRequestDto;
import com.example.clothsdanawa.store.dto.request.StoreFilterRequestDto;
import com.example.clothsdanawa.store.dto.request.StoreUpdateRequestDto;
import com.example.clothsdanawa.store.dto.response.StoreResponseDto;
import com.example.clothsdanawa.store.entity.Store;
import com.example.clothsdanawa.store.service.StoreService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/stores")
@RestController
@RequiredArgsConstructor
public class StoreController {

	private final StoreService storeService;
	private final RedisService redisService;

	@PostMapping
	public ResponseEntity<Void> createStore(
		@AuthenticationPrincipal CustomUserPrincipal customUserPrincipal,
		@RequestBody StoreCreateRequestDto requestDto
	) {

		String email = customUserPrincipal.getUsername();

		storeService.createStore(requestDto, email);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/{storeId}")
	public ResponseEntity<Void> updateStore(
		@AuthenticationPrincipal CustomUserPrincipal customUserPrincipal,
		@RequestBody StoreUpdateRequestDto storeUpdateRequestDto,
		@PathVariable Long storeId
	) {
		String email = customUserPrincipal.getUsername();

		storeService.updateStore(storeUpdateRequestDto, storeId, email);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Store>> getStoreList(
		@RequestParam(defaultValue = "0") Long cursor,
		@RequestParam(required = false) String keyword
	) {
		StoreFilterRequestDto requestDto = new StoreFilterRequestDto(cursor - 1, keyword);
		redisService.incrementCount(keyword);
		List<Store> storeList = storeService.getStoreList(requestDto);
		return new ResponseEntity<>(storeList, HttpStatus.OK);
	}

	@GetMapping("/{storeId}")
	public ResponseEntity<StoreResponseDto> getStore(
		@PathVariable Long storeId
	) {
		StoreResponseDto responseDto = storeService.getStore(storeId);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	@PatchMapping("/{storeId}")
	public ResponseEntity<Void> closeStore(
		@AuthenticationPrincipal CustomUserPrincipal customUserPrincipal,
		@PathVariable Long storeId
	) {
		String email = customUserPrincipal.getUsername();

		storeService.closeStore(storeId, email);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
