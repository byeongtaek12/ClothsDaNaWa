package com.example.clothsdanawa.cart.dto.response;

import com.example.clothsdanawa.cart.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
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
