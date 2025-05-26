package com.example.clothsdanawa.order.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.clothsdanawa.cart.entity.Cart;
import com.example.clothsdanawa.common.BaseEntity;
import com.example.clothsdanawa.user.entity.User;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
	private int totalPrice;

	@ElementCollection
	@CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
	private List<OrderItemInfo> cartList = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus orderStatus = OrderStatus.WAITING;

	@Column(nullable = false)
	private int point;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Builder
	public Order (Long quantity, int totalPrice, List<OrderItemInfo> cartList, OrderStatus orderStatus, int point, User user) {
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.cartList = cartList;
		this.orderStatus = orderStatus;
		this.point = point;
		this.user = user;
	}

	public void updateStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void updatePoint(int point) {
		this.point = point;
	}
}
