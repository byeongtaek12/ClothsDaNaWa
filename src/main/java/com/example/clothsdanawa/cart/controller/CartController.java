package com.example.clothsdanawa.cart.controller;

import com.example.clothsdanawa.cart.dto.request.CartCreateRequestDto;
import com.example.clothsdanawa.cart.dto.request.CartUpdateRequestDto;
import com.example.clothsdanawa.cart.dto.response.CartResponseDto;
import com.example.clothsdanawa.cart.service.CartService;
import com.example.clothsdanawa.common.security.CustomUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    //카트생성: 로그인 유저 id ,requestDto : 상품id,수량
    @PostMapping
    public ResponseEntity<CartResponseDto> createCart(@AuthenticationPrincipal CustomUserPrincipal userDetails,
                                                      @RequestBody @Valid CartCreateRequestDto requestDto) {

        return new ResponseEntity<>(cartService.createCart(userDetails.getId(), requestDto.getProductId(), requestDto.getQuantity()),
                HttpStatus.CREATED);
    }

    //장바구니 조회(사용자꺼만)
    @GetMapping
    public ResponseEntity<CartResponseDto> findCartList(@AuthenticationPrincipal CustomUserPrincipal userDetails) {
        return new ResponseEntity<>(cartService.findMyCart(userDetails.getId()),HttpStatus.OK);
    }

    //장바구니 수량변경 (0일때 삭제)
    @PatchMapping
    public ResponseEntity<CartResponseDto> updateCart(@AuthenticationPrincipal CustomUserPrincipal userDetails,
                                                      @RequestBody @Valid CartUpdateRequestDto requestDto){
        return new ResponseEntity<>(cartService.updateCart(userDetails.getId(),requestDto.getCartItemId(),requestDto.getQuantity()),
                HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCart(@AuthenticationPrincipal CustomUserPrincipal userDetails){
        cartService.deleteCart(userDetails.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}