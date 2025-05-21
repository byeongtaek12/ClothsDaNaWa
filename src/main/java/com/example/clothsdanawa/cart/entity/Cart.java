package com.example.clothsdanawa.cart.entity;

import com.example.clothsdanawa.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "carts")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartItem> cartItems = new ArrayList<>();

	private int totalPrice;
	private LocalDateTime expiredAt;


	public static Cart of(User user) {
		Cart cart = new Cart();
		cart.user = user;
		cart.expiredAt = LocalDateTime.now().plusDays(1);

		return cart;
	}

	//만료시간검증
	public boolean isExpired() {
		return this.expiredAt.isBefore(LocalDateTime.now());
	}

	//장바구니 총합금액계산
	public void calculateTotalPrice() {
		totalPrice = cartItems.stream()
			.mapToInt(CartItem::getTotalPrice)
			.sum();
	}

	public void refreshExpiredAt() {
		this.expiredAt = LocalDateTime.now().plusDays(1);
	}
}
