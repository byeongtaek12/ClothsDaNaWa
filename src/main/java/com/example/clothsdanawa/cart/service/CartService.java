package com.example.clothsdanawa.cart.service;


import com.example.clothsdanawa.cart.dto.response.CartResponseDto;
import com.example.clothsdanawa.cart.entity.Cart;
import com.example.clothsdanawa.cart.entity.CartItem;
import com.example.clothsdanawa.cart.repository.CartItemRepository;
import com.example.clothsdanawa.cart.repository.CartRepository;
import com.example.clothsdanawa.product.entity.Product;
import com.example.clothsdanawa.product.repository.ProductRepository;
import com.example.clothsdanawa.store.common.StoreStatus;
import com.example.clothsdanawa.user.entity.User;
import com.example.clothsdanawa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        //product 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 제품을 찾을 수 없습니다."));

        User user = userRepository.findById(userId).get();

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(Cart.of(user)));
        //status OPEN 상태인 쇼핑몰만
        if (!product.getStore().getStoreStatus().equals(StoreStatus.OPEN)) {
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
        //장바구니에 담긴 수량
        int cartQty = cartItem.map(CartItem::getQuantity).orElse(0);
        int totalQty = cartQty + quantity;

        //장바구니 수량이 stock 수량보다 많은지 검증
        if (product.getStock() < totalQty) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "요청 수량이 재고보다 많습니다.");
        }
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

    @Transactional(readOnly = true)
    public CartResponseDto findMyCart(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "장바구니가 없습니다."));

        if (cart.getCartItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "장바구니가 비어있습니다.");
        }
        cart.calculateTotalPrice();

        return CartResponseDto.from(cart);

    }

    @Transactional
    public CartResponseDto updateCart(Long userId, Long cartItemId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "장바구니가 없습니다."));
        //장바구니에 cartItemId 상품이 있는지 찾기
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId)).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "장바구니에 해당 상품이 없습니다."));
        //해당상품의 재고
        int stock = cartItem.getProduct().getStock();
        //재고수량이 부족할때
        if (stock < quantity) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "재고가 부족합니다.");
        }
        //0으로 수정할때 삭제
        cartItem.updateQuantity(cartItem, quantity);

        cart.calculateTotalPrice();

        return CartResponseDto.from(cart);
    }

    @Transactional
    public void deleteCart(Long userId){
        cartRepository.deleteById(userId);
    }
}
