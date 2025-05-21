package com.example.clothsdanawa.cart.dto.response;

import com.example.clothsdanawa.cart.entity.CartItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CartItemResponseDto {
    private final Long storeId;
    private final String storeName;
    private final Long productId;
    private final String productName;
    private final int price;
    private final int quantity;

    public static CartItemResponseDto from(CartItem cartItem) {
        return new CartItemResponseDto(
                cartItem.getProduct().getStore().getStoreId(),
                cartItem.getProduct().getStore().getCompany(),
                cartItem.getProduct().getId(),
                cartItem.getProduct().getProductName(),
                cartItem.getProduct().getPrice(),
                cartItem.getQuantity()
        );
    }
}
