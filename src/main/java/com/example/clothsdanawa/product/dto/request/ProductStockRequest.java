package com.example.clothsdanawa.product.dto.request;

import com.example.clothsdanawa.product.enums.StockOperationType;
import lombok.Getter;

/**
 * 재고 변경 요청 DTO
 */
@Getter
public class ProductStockRequest {

	private int quantity;
	private StockOperationType type; // INCREASE or DECREASE

	/**
	 * 생성자
	 * @param quantity 변경할 수량
	 * @param type 재고 변경 타입 (INCREASE or DECREASE)
	 */
	public ProductStockRequest(int quantity, StockOperationType type) {
		this.quantity = quantity;
		this.type = type;
	}
}