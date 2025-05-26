package com.example.clothsdanawa.order.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
* 주문 내용 저장
* */
@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemInfo {
	private String productName;
	private int quantity;
	private int price;
}
