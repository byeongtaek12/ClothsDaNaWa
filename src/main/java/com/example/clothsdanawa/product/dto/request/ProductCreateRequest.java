package com.example.clothsdanawa.product.dto.request;

import lombok.Getter;

/**
 * 상품 등록 요청 DTO
 */
@Getter
public class ProductCreateRequest {

	private Long storeId;
	private String productName;
	private int price;
	private int stock;
}
