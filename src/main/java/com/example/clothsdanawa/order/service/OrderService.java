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

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final CartRepository cartRepository;

	// 1. 주문 생성
	public OrderResponseDto createOrder(Long cartId, OrderRequestDto orderRequestDto) {
		if (orderRepository.findOrderByCartID(cartId).isPresent()) {
			throw new IllegalStateException("주문이 이미 생성되었습니다.");
		} // 중복요청 확인

		Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
			new EntityNotFoundException("Cart not found"));
		List<CartItem> cartItems = cart.getCartItems();

		Long totalQuantity = cartItems.stream()
			.mapToLong(item -> (long)item.getQuantity()) // 형변환
			.sum();

		int totalPrice = cart.getTotalPrice();

		Order order = Order.builder()
			.quantity(totalQuantity)
			.totalPrice(totalPrice)
			.orderStatus(OrderStatus.WAITING)
			.point(orderRequestDto.getPoint())
			.cart(cart)
			.build();

		return new OrderResponseDto(order, cart);
	}

	// 2. 주문 조회
	public OrderResponseDto findOrder(Long id) {
		Order order = orderRepository.findById(id).orElseThrow(() ->
			new EntityNotFoundException("Order not found"));

		Cart cart = order.getCart();

		return new OrderResponseDto(order, cart);

	}

	// 3. 주문 상태 변경
	public String updateStatus(Long id, OrderStatus newStatus) {
		Order order = orderRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Order not found"));

		order.updateStatus(newStatus);
		orderRepository.save(order);

		// 메시지 정의
		String message;
		switch (newStatus) {
			case IN_DELIVERY:
				message = "배송 중입니다.";
				break;
			case COMPLETED:
				message = "배송이 완료되었습니다.";
				break;
			default:
				message = "상태가 업데이트되었습니다.";
		}

		return message;
	}

	// 4. 주문 요청 취소
	public String cancelOrder(Long orderId) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new EntityNotFoundException("Order not found"));

		if (order.getOrderStatus() == OrderStatus.CANCELLED) {
			throw new IllegalStateException("이미 취소된 주문입니다.");
		}

		if (order.getOrderStatus() == OrderStatus.COMPLETED) {
			throw new IllegalStateException("배송 완료된 주문은 취소할 수 없습니다.");
		}

		order.updateStatus(OrderStatus.CANCELLED);
		orderRepository.save(order);

		return "주문이 취소되었습니다.";

	}
}
