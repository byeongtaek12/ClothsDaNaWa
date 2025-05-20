package com.example.clothsdanawa.order.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.clothsdanawa.cart.entity.Cart;
import com.example.clothsdanawa.cart.entity.CartItem;
import com.example.clothsdanawa.cart.repository.CartRepository;
import com.example.clothsdanawa.order.dto.OrderRequestDto;
import com.example.clothsdanawa.order.dto.OrderResponseDto;
import com.example.clothsdanawa.order.entity.Order;
import com.example.clothsdanawa.order.entity.OrderStatus;
import com.example.clothsdanawa.order.repository.OrderRepository;
import com.example.clothsdanawa.product.service.ProductService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final CartRepository cartRepository;
	private final ProductService productService;

	// 1. 주문 생성
	public OrderResponseDto createOrder(Long cartId, OrderRequestDto orderRequestDto) {

		if (orderRepository.findOrderByCartID(cartId).isPresent()) {
			throw new IllegalStateException("주문이 이미 생성되었습니다.");
		} // 중복요청 확인

		Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
			new EntityNotFoundException("장바구니를 찾을 수 없습니다."));

		// 장바구니 내역 가져오기
		List<CartItem> cartItems = cart.getCartItems();

		// 총 수량 계산
		Long totalQuantity = cartItems.stream()
			.mapToLong(item -> (long)item.getQuantity()) // 형변환
			.sum();

		// 총 금액 가져오기
		Long totalPrice = (long)cart.getTotalPrice();

		// 포인트 차감
		// (입력된 포인트) - (총액수) -> (남은 포인트)
		Long point = orderRequestDto.getPoint();
		Long afterPayment = (point - totalPrice);

		// 상품 재고 차감
		for (CartItem item : cartItems) {
			Long productId = item.getProduct().getId();
			int quantity = item.getQuantity();
			productService.decreaseStock(productId, quantity);
		}

		// 주문 저장
		Order order = Order.builder()
			.quantity(totalQuantity)
			.totalPrice(totalPrice)
			.orderStatus(OrderStatus.WAITING)
			.point(afterPayment)
			.cart(cart)
			.build();

		orderRepository.save(order);

		return new OrderResponseDto(order, cart);
	}

	// 2. 주문 조회
	public OrderResponseDto findOrder(Long id) {
		Order order = orderRepository.findById(id).orElseThrow(() ->
			new EntityNotFoundException("주문을 찾을 수 없습니다."));

		Cart cart = order.getCart();

		return new OrderResponseDto(order, cart);
	}

	// 3. 주문 상태 변경
	public String updateStatus(Long id, OrderStatus newStatus) {

		Order order = orderRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));

		// 상태 수정
		order.updateStatus(newStatus);
		orderRepository.save(order);

		// 입력된 상태별 메시지 출력
		String message;
		switch (newStatus) {
			case IN_DELIVERY:
				message = "배송 중입니다.";
				break;
			case COMPLETED:
				message = "배송이 완료되었습니다.";
				break;
			default:
				message = " ";
		}

		return message;
	}

	// 4. 주문 요청 취소
	public String cancelOrder(Long orderId) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));

		// 중복 취소 예외
		if (order.getOrderStatus() == OrderStatus.CANCELLED) {
			throw new IllegalStateException("이미 취소된 주문입니다.");
		}

		// 상태별 취소 예외
		if (order.getOrderStatus() == OrderStatus.COMPLETED) {
			throw new IllegalStateException("배송 완료된 주문은 취소할 수 없습니다.");
		}

		// 주문 취소 상태 변경 -> 취소 주문내역 조회 가능
		order.updateStatus(OrderStatus.CANCELLED);
		orderRepository.save(order);

		return "주문이 취소되었습니다.";

	}
}
