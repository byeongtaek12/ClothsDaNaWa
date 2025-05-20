package com.example.clothsdanawa.store.service;

import java.util.List;

import com.example.clothsdanawa.store.entity.StoreStatus;
import com.example.clothsdanawa.store.dto.request.StoreCreateRequestDto;
import com.example.clothsdanawa.store.dto.request.StoreFilterRequestDto;
import com.example.clothsdanawa.store.dto.response.StoreResponseDto;
import com.example.clothsdanawa.store.entity.Store;

public interface StoreService {
	void createStore(StoreCreateRequestDto requestDto, String email);

	void approveStore(Long storeId);

	List<Store> getPendingStore(StoreStatus status);

	List<Store> getStoreList(StoreFilterRequestDto requestDto);

	StoreResponseDto getStore(Long storeId);

	void closeStore(Long storeId, String email);
}
