package com.example.clothsdanawa.product.dto.response;

import com.example.clothsdanawa.product.entity.Product;
import lombok.Getter;

/**
 * 상품 재고 변경 응답 DTO (증가 / 차감)
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