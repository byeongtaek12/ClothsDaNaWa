package com.example.clothsdanawa.store.repository;

import java.util.List;

import com.example.clothsdanawa.store.dto.request.StoreFilterRequestDto;
import com.example.clothsdanawa.store.entity.Store;

public interface StoreRepositoryQuery {
	List<Store> findStoresByKeywordWithCursorOrderByUpdatedAt(StoreFilterRequestDto requestDto);
}
