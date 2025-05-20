package com.example.clothsdanawa.order.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clothsdanawa.order.dto.OrderRequestDto;
import com.example.clothsdanawa.order.dto.OrderResponseDto;
import com.example.clothsdanawa.order.entity.Order;
import com.example.clothsdanawa.order.entity.OrderStatus;
import com.example.clothsdanawa.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;

	// todo : 1. 주문 생성
	public OrderResponseDto createOrder(Long cartId, OrderRequestDto orderRequestDto) {
		// if(orderRepository.findOrderByCartID(cartId).isPresent()){throw new 예외처리} 중복요청 확인

		// Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
		// 	new InvalidRequestException("Cart not found"));
		// List<Cart> cartItems = cart.getCartItem();

		// Long totalQuantity = cartItem.stream()
		// 		.mapToLong(CartItem::getQuantity)
		// 		.sum();
		//
		// 	Long totalPrice = cartItem.stream()
		// 		.mapToLong(item -> item.getProduct().getPrice() * item.getQuantity())
		// 		.sum();

		Order order = Order.builder()
			//.quantity(totalQuantity)
			//.totalPrice(totalPrice)
			.orderStatus(OrderStatus.WAITING)
			.point(orderRequestDto.getPoint())
			//.cart(cart)
			.build();

		Order savedOrder = orderRepository.save(order);

		return new OrderResponseDto(
			savedOrder.getId(),
			//savedOrder.getCartId(),
			savedOrder.getQuantity(),
			// savedOrder.getCartList(),
			// savedOrder.getTotalPrice(),
			savedOrder.getPoint(),
			savedOrder.getCreatedAt(),
			savedOrder.getOrderStatus()
			//cartItems
		);
	}

	// todo : 2. 주문 조회
	// public OrderResponseDto findOrder(Long id){
	// 	Order order = orderRepository.findById(id);//.orElseThrow(() ->
	// 		//new InvalidRequestException("Order not found"));
	//
	// 	// Cart cart = order.getCart();
	//
	// 	// List<Cart> cartItems = cart.getCartItem();
	//
	// 	return new OrderResponseDto(
	// 		order.getId(),
	// 		//order.getCartId(),
	// 		order.getQuantity(),
	// 		// order.getCartList(),
	// 		// order.getTotalPrice
	// 		order.getPoint(),
	// 		order.getCreatedAt(),
	// 		order.getOrderStatus()
	// 		//cartItems
	// 	);
	// }
	//
	// // todo : 3. 주문 상태 변경
	// public String updateStatus(Long id, OrderStatus newStatus) {
	// 	Order order = orderRepository.findById(id);
	// 		//.orElseThrow(() -> new InvalidRequestException("Order not found"));
	//
	// 	//order.setOrderStatus(newStatus);
	// 	orderRepository.save(order);
	//
	// 	// 메시지 정의
	// 	String message;
	// 	switch (newStatus) {
	// 		case IN_DELIVERY:
	// 			message = "배송 중입니다.";
	// 			break;
	// 		case COMPLETED:
	// 			message = "배송이 완료되었습니다.";
	// 			break;
	// 		default:
	// 			message = "상태가 업데이트되었습니다.";
	// 	}
	//
	// 	return message;
	// }
	//
	// // todo : 4. 주문 요청 취소
	// public String cancelOrder(Long orderId) {
	// 	Order order = orderRepository.findById(orderId);
	// 		//.orElseThrow(() -> new InvalidRequestException("Order not found"));
	//
	// 	// if (order.getOrderStatus() == OrderStatus.CANCELLED) {throw new 예외처리("이미 취소된 주문입니다.");
	//
	// 	// if (order.getOrderStatus() == OrderStatus.COMPLETED) {throw new 예외처리("배송 완료된 주문은 취소할 수 없습니다.");
	//
	// 	order.setOrderStatus(OrderStatus.CANCELLED);
	// 	orderRepository.save(order);
	//
	// 	return "주문이 취소되었습니다.";
	// }

}
