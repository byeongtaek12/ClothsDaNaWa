package com.example.clothsdanawa.order.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.example.clothsdanawa.order.entity.Order;
import com.example.clothsdanawa.order.entity.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderResponseDto {
	private final Long orderId;
	//private final Long cartId;
	private final Long quantity;
	//private final List<CartListResponseDto> cartList;
	//private final Long totalPrice;
	private final Long point;
	private final LocalDateTime created_at;
	private final OrderStatus orderStatus;

	// , Cart cart
	// public OrderResponseDto(Order order) {
	// 	this.orderId = order.getId();
	// 	//this.cartId = order.getCartID();
	// 	this.quantity = order.getQuantity();
	// 	//this.cartList =
	// 	//this.total_price = cart.getTotalPrice();
	// 	this.point = order.getPoint();
	// 	this.created_at = order.getCreatedAt();
	// 	this.orderStatus = order.getOrderStatus();
	// }
}
