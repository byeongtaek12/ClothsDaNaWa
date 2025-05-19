package com.example.clothsdanawa.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.clothsdanawa.store.common.StoreStatus;
import com.example.clothsdanawa.store.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
	Optional<Store> findByStoreId(Long storeId);

	List<Store> findAllByStoreStatusOrderByCreateAtAsc(StoreStatus status);

	default Store findByStoreIdOrElseThrow(Long storeId) {
		return findByStoreId(storeId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
}