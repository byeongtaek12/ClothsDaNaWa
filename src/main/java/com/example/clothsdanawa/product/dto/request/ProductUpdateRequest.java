package com.example.clothsdanawa.product.dto.request;

import lombok.Getter;

/**
 * 상품 수정 요청 DTO
 */
@Getter
public class ProductUpdateRequest {

	private String productName;
	private int price;
	private int stock;
}
