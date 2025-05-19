package com.example.clothsdanawa.order.dto;

import java.time.LocalTime;

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
	private final Long total_price;
	private final Long point;
	private final LocalTime created_at;
	private final OrderStatus orderStatus;

}
