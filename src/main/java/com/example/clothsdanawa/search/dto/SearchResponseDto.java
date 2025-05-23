package com.example.clothsdanawa.search.dto;

import com.example.clothsdanawa.product.entity.Product;
import com.example.clothsdanawa.store.entity.Store;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SearchResponseDto {

	private final Long id;
	private final String type;  //가게인지 상품인지
	private final String name;  //이름
	private final String info;  //가게면 전화번호, 상품이면 가격

	public static SearchResponseDto from(Store store) {
		return new SearchResponseDto(
			store.getStoreId(),
			"store",
			store.getCompany(),
			store.getAddress() + " : " + store.getStoreNumber());
	}

	public static SearchResponseDto from(Product product) {
		return new SearchResponseDto(
			product.getId(),
			"product",
			product.getProductName(),
			product.getStore().getCompany() + " | " + product.getPrice() + "원");
	}
}
