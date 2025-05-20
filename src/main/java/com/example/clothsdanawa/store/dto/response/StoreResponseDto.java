package com.example.clothsdanawa.store.dto.response;

import com.example.clothsdanawa.store.entity.StoreStatus;
import com.example.clothsdanawa.store.entity.Store;

import lombok.Builder;

@Builder
public class StoreResponseDto {
	private Long storeId;
	private String company;
	private StoreStatus storeStatus;
	private String storeNumber;
	private String address;
	private Long userId;
	private String name;

	public StoreResponseDto(Store store) {
		this.storeId = store.getStoreId();
		this.company = store.getCompany();
		this.storeStatus = store.getStoreStatus();
		this.storeNumber = store.getStoreNumber();
		this.address = store.getStoreNumber();
		this.userId = store.getUser().getUserId();
		this.name = store.getUser().getName();
	}
}
