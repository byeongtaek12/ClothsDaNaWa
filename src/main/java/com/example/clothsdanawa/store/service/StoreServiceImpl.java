package com.example.clothsdanawa.store.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.clothsdanawa.store.entity.StoreStatus;
import com.example.clothsdanawa.store.dto.request.StoreCreateRequestDto;
import com.example.clothsdanawa.store.dto.request.StoreFilterRequestDto;
import com.example.clothsdanawa.store.dto.response.StoreResponseDto;
import com.example.clothsdanawa.store.entity.Store;
import com.example.clothsdanawa.store.repository.StoreRepository;
import com.example.clothsdanawa.store.repository.StoreRepositoryQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
	private final StoreRepository storeRepository;
	private final StoreRepositoryQuery storeRepositoryQuery;

	@Override
	public void createStore(StoreCreateRequestDto requestDto) {
		Store store = new Store(requestDto);
		storeRepository.save(store);
	}

	@Override
	@Transactional
	public void approveStore(Long storeId) {
		Store pendingStore = storeRepository.findByStoreIdOrElseThrow(storeId);
		pendingStore.approveStore();
	}

	@Override
	public List<Store> getPendingStore(StoreStatus status) {
		return storeRepository.findAllByStoreStatusOrderByCreateAtAsc(status);
	}

	@Override
	public List<Store> getStoreList(StoreFilterRequestDto requestDto) {
		return storeRepositoryQuery.findStoresByKeywordWithCursorOrderByUpdatedAt(requestDto);
	}

	@Override
	public StoreResponseDto getStore(Long storeId) {
		Store store = storeRepository.findByStoreIdOrElseThrow(storeId);
		return new StoreResponseDto(store);
	}

	@Transactional
	@Override
	public void closeStore(Long storeId) {
		Store store = storeRepository.findByStoreIdOrElseThrow(storeId);
		store.closeStore();
	}
}
