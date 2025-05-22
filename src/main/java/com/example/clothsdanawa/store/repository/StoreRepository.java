package com.example.clothsdanawa.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.clothsdanawa.common.exception.BaseException;
import com.example.clothsdanawa.common.exception.ErrorCode;
import com.example.clothsdanawa.store.entity.StoreStatus;
import com.example.clothsdanawa.store.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
	Optional<Store> findByStoreIdAndStoreStatus(Long storeId, StoreStatus storeStatus);

	List<Store> findAllByStoreStatusOrderByCreatedAtAsc(StoreStatus status);

	// storeId로 OPEN 상태의 store를 검색
	default Store findByStoreIdOrElseThrow(Long storeId) {
		return findByStoreIdAndStoreStatus(storeId, StoreStatus.OPEN)
			.orElseThrow(() -> new BaseException(ErrorCode.STORE_NOT_FOUND));
	}

	// storeId로 PENDING 상태의 store를 검색
	default Store findPendingStore(Long storeId) {
		return findByStoreIdAndStoreStatus(storeId, StoreStatus.PENDING)
			.orElseThrow(() -> new BaseException(ErrorCode.STORE_NOT_FOUND));
	}
}