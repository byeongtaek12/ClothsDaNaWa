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
}
