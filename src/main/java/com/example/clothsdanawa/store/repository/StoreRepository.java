package com.example.clothsdanawa.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.clothsdanawa.store.entity.Store;
import com.example.clothsdanawa.store.entity.StoreStatus;

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

	@Query("SELECT s FROM Store s WHERE s.company LIKE %:keyword% AND s.storeStatus = 'OPEN'")
	List<Store> searchStoreByKeyword(@Param("keyword") String keyword);

}