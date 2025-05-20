package com.example.clothsdanawa.store.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.clothsdanawa.store.entity.StoreStatus;
import com.example.clothsdanawa.store.dto.request.StoreCreateRequestDto;
import com.example.clothsdanawa.store.dto.request.StoreFilterRequestDto;
import com.example.clothsdanawa.store.dto.response.StoreResponseDto;
import com.example.clothsdanawa.store.entity.Store;
import com.example.clothsdanawa.store.repository.StoreRepository;
import com.example.clothsdanawa.store.repository.StoreRepositoryQuery;
import com.example.clothsdanawa.user.entity.User;
import com.example.clothsdanawa.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
	private final StoreRepository storeRepository;
	private final StoreRepositoryQuery storeRepositoryQuery;
	private final UserRepository userRepository;

	@Override
	@Transactional
	public void createStore(StoreCreateRequestDto requestDto, String email) {
		User user = userRepository.findByEmailOrElseThrow(email);
		Store store = new Store(requestDto);
		store.setUser(user);
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
		return storeRepository.findAllByStoreStatusOrderByCreatedAtAsc(status);
	}

	@Override
	public List<Store> getStoreList(StoreFilterRequestDto requestDto) {
		return storeRepositoryQuery.findStoresByKeywordWithCursorOrderByUpdatedAt(requestDto);
	}

	@Override
	public StoreResponseDto getStore(Long storeId) {
		Store store = storeRepository.findByStoreIdOrElseThrow(storeId);
		return StoreResponseDto.from(store);
	}

	@Transactional
	@Override
	public void closeStore(Long storeId, String email) {
		User ownerUser = userRepository.findByEmailOrElseThrow(email);
		Store store = storeRepository.findByStoreIdOrElseThrow(storeId);
		if(store.getUser() != ownerUser){
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		store.closeStore();
	}
}
