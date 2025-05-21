package com.example.clothsdanawa.cart.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CartUpdateRequestDto {

    @NotNull
    private final Long cartItemId;

    @NotNull
    @Min(0)
    private final int quantity;
}