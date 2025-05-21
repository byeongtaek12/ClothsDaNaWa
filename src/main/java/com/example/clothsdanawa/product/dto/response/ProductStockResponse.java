package com.example.clothsdanawa.product.dto.response;

import com.example.clothsdanawa.product.entity.Product;

import lombok.Getter;

/**
 * 상품 재고 차감 응답 DTO
 */
@Getter
public class ProductStockResponse {

	private final Long productId;
	private final String productName;
	private final int stock;

	public ProductStockResponse(Product product) {
		this.productId = product.getId();
		this.productName = product.getProductName();
		this.stock = product.getStock();
	}
}
