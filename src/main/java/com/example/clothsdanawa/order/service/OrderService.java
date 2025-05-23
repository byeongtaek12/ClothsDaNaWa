package com.example.clothsdanawa.order.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.clothsdanawa.cart.entity.Cart;
import com.example.clothsdanawa.cart.entity.CartItem;
import com.example.clothsdanawa.cart.repository.CartRepository;
import com.example.clothsdanawa.cart.service.CartService;
import com.example.clothsdanawa.common.exception.BaseException;
import com.example.clothsdanawa.common.exception.ErrorCode;
import com.example.clothsdanawa.order.dto.OrderRequestDto;
import com.example.clothsdanawa.order.dto.OrderResponseDto;
import com.example.clothsdanawa.order.entity.Order;
import com.example.clothsdanawa.order.entity.OrderItemInfo;
import com.example.clothsdanawa.order.entity.OrderStatus;
import com.example.clothsdanawa.order.repository.OrderRepository;
import com.example.clothsdanawa.product.service.ProductService;
import com.example.clothsdanawa.user.entity.User;
import com.example.clothsdanawa.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final CartRepository cartRepository;
	private final ProductService productService;
	private final CartService cartService;
	private final UserRepository userRepository;


	// 1. 주문 생성
	@Transactional
	public OrderResponseDto createOrder(Long userId, OrderRequestDto orderRequestDto) {

		if (orderRepository.findByUserId(userId).isPresent()) {
			throw new BaseException(ErrorCode.ORDER_DUPLICATE_CREATION);
		} // 중복요청 확인

		Cart cart = cartRepository.findByUserId(userId).orElseThrow(() ->//
			new BaseException(ErrorCode.CART_NOT_FOUND));

		User user = userRepository.findById(userId).orElseThrow(() ->
			new BaseException(ErrorCode.USER_NOT_FOUND));

		// 장바구니 내역 가져오기
		List<CartItem> cartItems = cart.getCartItems();

		// 총 수량 계산
		Long totalQuantity = cartItems.stream()
			.mapToLong(item -> (long)item.getQuantity()) // 형변환
			.sum();

		// 총 금액 가져오기
		int totalPrice = cart.getTotalPrice();

		if (orderRequestDto.getPoint() < totalPrice) {
			throw new BaseException(ErrorCode.INSUFFICIENT_POINT);
		}

		// 포인트 차감
		// (입력된 포인트) - (총액수) -> (남은 포인트)
		int point = orderRequestDto.getPoint();
		int afterPayment = (point - totalPrice);


		// 상품 재고 차감
		for (CartItem item : cartItems) {
			Long productId = item.getProduct().getId();
			int quantity = item.getQuantity();
			productService.decreaseStock(productId, quantity);
		}

		// 장바구니 내역 orderItemInfos으 정의
		List<OrderItemInfo> orderItemInfos = cartItems.stream()
			.map(item -> new OrderItemInfo(
				item.getProduct().getProductName(),
				item.getQuantity(),
				item.getProduct().getPrice()
			))
			.collect(Collectors.toList());

		// 주문 저장
		Order order = Order.builder()
			.quantity(totalQuantity)
			.totalPrice(totalPrice)
			.cartList(orderItemInfos) // 장바구니 내역 저장
			.orderStatus(OrderStatus.WAITING)
			.point(afterPayment)
			.user(user)
			.build();

		orderRepository.save(order);

		// 주문생성 후 장바구니 삭제
		// cartService.deleteCart(userId);
		cartService.deleteCart(cart.getUser().getUserId());

		return new OrderResponseDto(order);
	}

	// 2. 주문 조회
	public OrderResponseDto findOrder(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() ->
			new BaseException(ErrorCode.ORDER_NOT_FOUND));

		return new OrderResponseDto(order);
	}

	// 3. 주문 상태 변경
	public String updateStatus(Long orderId, OrderStatus newStatus) {

		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new BaseException(ErrorCode.ORDER_NOT_FOUND));

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
			.orElseThrow(() -> new BaseException(ErrorCode.ORDER_NOT_FOUND));

		// 중복 취소 예외
		if (order.getOrderStatus() == OrderStatus.CANCELLED) {
			throw new BaseException(ErrorCode.ALREADY_CANCELLED);
		}

		// 상태별 취소 예외
		if (order.getOrderStatus() == OrderStatus.COMPLETED) {
			throw new BaseException(ErrorCode.ALREADY_DELIVERED);
		}

		// 포인트 반환
		int usedPoint = order.getTotalPrice();
		int point = order.getPoint();
		int newPoint = usedPoint + point;

		order.updatePoint(newPoint);

		// todo : 차감된 재고 복원
		//
		// List<OrderItemInfo> cartItems = order.getCartList();
		// for (CartItem item : cartItems) {
		// 	Long productId = item.getProduct().getId();
		// 	int quantity = item.getQuantity();
		// 	productService.decreaseStock(productId, quantity);
		// }

		// 주문 취소 상태 변경 -> 취소 주문내역 조회 가능
		order.updateStatus(OrderStatus.CANCELLED);
		orderRepository.save(order);

		return "주문이 취소되었습니다.";

	}
}
