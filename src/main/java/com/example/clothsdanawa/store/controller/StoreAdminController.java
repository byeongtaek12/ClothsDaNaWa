package com.example.clothsdanawa.store.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.clothsdanawa.store.entity.StoreStatus;
import com.example.clothsdanawa.store.entity.Store;
import com.example.clothsdanawa.store.service.StoreService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin/stores")
@RestController
@RequiredArgsConstructor
public class StoreAdminController {
	private final StoreService storeService;

	@PatchMapping("/{storeId}/approve")
	public ResponseEntity<Void> approveStore(
		@PathVariable Long storeId
	) {
		storeService.approveStore(storeId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Store>> getPendingStore() {
		List<Store> storeList = storeService.getPendingStore();
		return new ResponseEntity<>(storeList, HttpStatus.OK);
	}
}
