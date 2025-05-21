package com.example.clothsdanawa.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.clothsdanawa.store.entity.StoreStatus;
import com.example.clothsdanawa.store.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
	Optional<Store> findByStoreIdAndStoreStatus(Long storeId, StoreStatus storeStatus);

	List<Store> findAllByStoreStatusOrderByCreatedAtAsc(StoreStatus status);

	default Store findByStoreIdOrElseThrow(Long storeId) {
		return findByStoreIdAndStoreStatus(storeId, StoreStatus.OPEN)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	default Store findPendingStore(Long storeId) {
		return findByStoreIdAndStoreStatus(storeId, StoreStatus.PENDING)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
}