package com.example.clothsdanawa.store.repository;

import com.example.clothsdanawa.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

// 테스트용 레포 지우셈

public interface StoreRepository extends JpaRepository<Store, Long> {
}
