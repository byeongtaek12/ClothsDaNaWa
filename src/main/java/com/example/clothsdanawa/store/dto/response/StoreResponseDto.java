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

	public static StoreResponseDto from(Store store) {
		return StoreResponseDto.builder()
			.storeId(store.getStoreId())
			.company(store.getCompany())
			.storeStatus(store.getStoreStatus())
			.storeNumber(store.getStoreNumber())
			.address(store.getAddress())
			.userId(store.getUser().getUserId())
			.name(store.getUser().getName())
			.build();

	}
}
