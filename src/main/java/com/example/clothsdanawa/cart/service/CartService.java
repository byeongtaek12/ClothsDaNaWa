package com.example.clothsdanawa.cart.service;


import com.example.clothsdanawa.cart.dto.response.CartResponseDto;
import com.example.clothsdanawa.cart.entity.Cart;
import com.example.clothsdanawa.cart.entity.CartItem;
import com.example.clothsdanawa.cart.repository.CartItemRepository;
import com.example.clothsdanawa.cart.repository.CartRepository;
import com.example.clothsdanawa.product.ProductRepository;
import com.example.clothsdanawa.product.entity.Product;
import com.example.clothsdanawa.user.User;
import com.example.clothsdanawa.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    //장바구니생성
    @Transactional
    public CartResponseDto createCart(Long userId, Long productId, int quantity) {
        /*
         * 1. product 조회
         * 2. product stock<quantity 면 예외처리
         * 3. user의 장바구니가 있는지 조회: 없으면 생성
         * 4. 가게 업소상태 OPEN이 아니면 주문x
         * 5. 가게 product 수량 조회:주문수량보다 적으면 x
         * */

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 제품을 찾을 수 없습니다."));
        if (product.getStock() < quantity) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "요청 수량이 재고보다 많습니다.");
        }

        User user = userRepository.findById(userId).get();

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(Cart.of(user)));

        if (!product.getStore().getStatus().equals("OPEN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "운영중인 쇼핑몰이 아닙니다.");
        }
        //장바구니 만료시간조회 만료시 장바구니비우기
        if (cart.isExpired()) {
            cart.getCartItems().clear();
            cart.refreshExpiredAt();
        }
        // 장바구니에 해당상품이들어있는지 확인
        Optional<CartItem> cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        //있으면 수량추가
        if (cartItem.isPresent()) {
            cartItem.get().addQuantity(quantity);
        }
        //없으면 장바구니에 추가
        else {
            CartItem addCartItem = CartItem.of(cart, product, quantity);
            cart.getCartItems().add(addCartItem);
        }
        cart.calculateTotalPrice();
        cartRepository.save(cart);

        return CartResponseDto.from(cart);
    }
}
