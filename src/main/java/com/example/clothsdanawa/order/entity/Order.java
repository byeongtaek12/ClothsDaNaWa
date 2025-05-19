package com.example.clothsdanawa.order.entity;

import java.time.LocalTime;

import com.example.clothsdanawa.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long quantity;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus orderStatus = OrderStatus.WAITING;

	@Column(nullable = false)
	private Long Point;

	//
	// @OneToOne
	// JoinColumn(name = "cart_id")
	// private Cart cart;

	// todo : cart 넣어야함
	@Builder
	public Order(Long quantity, OrderStatus orderStatus, Long point) {
		this.quantity = quantity;
		this.orderStatus = orderStatus;
		Point = point;
		//this.cart = cart;
	}
}
