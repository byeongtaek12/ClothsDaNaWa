package com.example.clothsdanawa.cart.dto.response;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.example.clothsdanawa.cart.entity.Cart;

@Getter
@RequiredArgsConstructor
public class CartResponseDto {

	private final Long cartId;
	private final int totalPrice;
	private final LocalDateTime expiredAt;
	private final List<CartItemResponseDto> cartItems;


	public static CartResponseDto from(Cart cart) {
		List<CartItemResponseDto> cartItemList = cart.getCartItems().stream()
			.map(CartItemResponseDto::from)
			.toList();

		return new CartResponseDto(
			cart.getId(),
			cart.getTotalPrice(),
			cart.getExpiredAt(),
			cartItemList
		);
	}
}