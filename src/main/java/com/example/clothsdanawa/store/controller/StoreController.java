package com.example.clothsdanawa.store.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.clothsdanawa.store.entity.Store;
import com.example.clothsdanawa.store.repository.StoreRepository;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;

// 테스트용 임시 컨트롤로롤ㄹ 지우셈

@RestController
@RequiredArgsConstructor
@RequestMapping("/test-store")
@PermitAll
public class StoreController {

	private final StoreRepository storeRepository;

	@PostMapping
	public Long createDummyStore() {
		Store store = Store.builder()
			.userId(1L)
			.company("테스트상점")
			.status("OPEN")
			.storeNumber("02-1234-5678")
			.address("서울시 어딘가")
			.build();

		return storeRepository.save(store).getId();
	}
}
