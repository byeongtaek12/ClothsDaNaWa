package com.example.clothsdanawa.store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreUpdateRequestDto {
	private String company;
	private String storeNumber;
	private String address;
}
