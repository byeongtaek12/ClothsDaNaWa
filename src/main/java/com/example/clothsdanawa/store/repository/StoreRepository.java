package com.example.clothsdanawa.store.repository;

import com.example.clothsdanawa.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Store 엔티티의 기본 CRUD 처리를 위한 최소한의 Repository
 */
@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}
