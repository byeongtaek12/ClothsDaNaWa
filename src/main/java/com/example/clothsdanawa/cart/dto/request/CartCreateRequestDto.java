package com.example.clothsdanawa.cart.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class CartCreateRequestDto {

	@NotNull
	@Min(1)
	private final Long productId;

	@NotNull
	@Min(1)
	private final int quantity;

}

