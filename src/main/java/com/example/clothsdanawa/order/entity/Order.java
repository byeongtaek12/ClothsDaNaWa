package com.example.clothsdanawa.order.entity;

import java.time.LocalTime;
import java.util.List;

import com.example.clothsdanawa.cart.entity.Cart;
import com.example.clothsdanawa.cart.entity.CartItem;
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

	@Column(nullable = false)
	private Long totalPrice;

	@Column
	private List<CartItem> cartList;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus orderStatus = OrderStatus.WAITING;

	@Column(nullable = false)
	private Long point;

	@OneToOne
	@JoinColumn(name = "cart_id")
	private Cart cart;

	@Builder
	public Order (Long quantity, Long totalPrice, List<CartItem> cartList, OrderStatus orderStatus, Long point, Cart cart) {
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.cartList = cartList;
		this.orderStatus = orderStatus;
		this.point = point;
		this.cart = cart;
	}

	public void updateStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void updatePoint(Long point) {
		this.point = point;
	}
}
