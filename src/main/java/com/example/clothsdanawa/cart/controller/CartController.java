package com.example.clothsdanawa.cart.controller;

import com.example.clothsdanawa.cart.dto.request.CartCreateRequestDto;
import com.example.clothsdanawa.cart.dto.response.CartReponseDto;
import com.example.clothsdanawa.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/carts")
public class CartController {

    private final CartService cartService;

    //카트생성: 유저정보 , 메뉴정보(0가게정보)
    @PostMapping
    public ResponseEntity<CartReponseDto> createCart(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                     @RequestBody @Valid CartCreateRequestDto requestDto) {

        return new ResponseEntity<>(cartService.createCart(userDetails.getId, requestDto.getProductId(), requestDto.getQuantity()), HttpStatus.CREATED);
    }
}