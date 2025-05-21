package com.example.clothsdanawa.store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class StoreFilterRequestDto {
	private Long cursor;
	private String keyword;
}
