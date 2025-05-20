package com.example.clothsdanawa.product.dto.response;

import com.example.clothsdanawa.product.entity.Product;
import lombok.Getter;

/**
 * 상품 응답 DTO
 * 클라이언트에게 노출할 상품 정보를 정제하여 전달
 */
@Getter
public class ProductResponse {

	private final Long id;
	private final String productName;
	private final int price;
	private final int stock;
	private final String company;

	public ProductResponse(Product product) {
		this.id = product.getId();
		this.productName = product.getProductName();
		this.price = product.getPrice();
		this.stock = product.getStock();
		this.company = product.getStore().getCompany(); // 연관 엔티티에서 쇼핑몰 이름 추출
	}
}
