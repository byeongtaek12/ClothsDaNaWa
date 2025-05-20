package com.example.clothsdanawa.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 상품 등록 성공 시 응답 DTO
 */
@Getter
public class ProductCreateResponse {
	private final Long productId;

	public ProductCreateResponse(Long productId) {
		this.productId = productId;
	}

	public Long getProductId() {
		return productId;
	}
}

