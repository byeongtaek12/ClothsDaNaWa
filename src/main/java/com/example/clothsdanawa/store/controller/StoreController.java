package com.example.clothsdanawa.store.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.clothsdanawa.store.dto.request.StoreCreateRequestDto;
import com.example.clothsdanawa.store.dto.request.StoreFilterRequestDto;
import com.example.clothsdanawa.store.dto.response.StoreResponseDto;
import com.example.clothsdanawa.store.entity.Store;
import com.example.clothsdanawa.store.service.StoreService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/stores")
@RestController
@RequiredArgsConstructor
public class StoreController {

	private final StoreService storeService;

	@PostMapping
	public ResponseEntity<Void> createStore(
		@RequestBody StoreCreateRequestDto requestDto
	) {
		storeService.createStore(requestDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Store>> getStoreList(
		@RequestParam Long cursor,
		@RequestParam(required = false) String keyword
	) {
		StoreFilterRequestDto requestDto = new StoreFilterRequestDto(cursor, keyword);
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
}
