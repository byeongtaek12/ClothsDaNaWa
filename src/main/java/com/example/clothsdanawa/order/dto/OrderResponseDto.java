package com.example.clothsdanawa.order.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.example.clothsdanawa.cart.dto.response.CartResponseDto;
import com.example.clothsdanawa.cart.entity.Cart;
import com.example.clothsdanawa.cart.entity.CartItem;
import com.example.clothsdanawa.order.entity.Order;
import com.example.clothsdanawa.order.entity.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderResponseDto {
	private final Long orderId;
	private final Long cartId;
	private final Long quantity;
	private final List<CartItem> cartList;
	private final int totalPrice;
	private final Long point;
	private final LocalDateTime created_at;
	private final OrderStatus orderStatus;

	public OrderResponseDto(Order order, Cart cart) {
		this.orderId = order.getId();
		this.cartId = cart.getId();
		this.quantity = order.getQuantity();
		this.cartList = cart.getCartItems();
		this.totalPrice = cart.getTotalPrice();
		this.point = order.getPoint();
		this.created_at = order.getCreatedAt();
		this.orderStatus = order.getOrderStatus();
	}
}
