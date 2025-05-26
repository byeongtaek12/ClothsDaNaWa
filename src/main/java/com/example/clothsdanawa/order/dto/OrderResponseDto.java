package com.example.clothsdanawa.order.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.clothsdanawa.order.entity.Order;
import com.example.clothsdanawa.order.entity.OrderItemInfo;
import com.example.clothsdanawa.order.entity.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderResponseDto {
	private final Long orderId;
	private final Long quantity;
	private final List<OrderItemInfo> cartList;
	private final int totalPrice;
	private final int point;
	private final LocalDateTime created_at;
	private final OrderStatus orderStatus;

	public OrderResponseDto(Order order) {
		this.orderId = order.getId();
		this.quantity = order.getQuantity();
		this.cartList = order.getCartList();
		this.totalPrice = order.getTotalPrice();
		this.point = order.getPoint();
		this.created_at = order.getCreatedAt();
		this.orderStatus = order.getOrderStatus();
	}
}
