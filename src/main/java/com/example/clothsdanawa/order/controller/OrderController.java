package com.example.clothsdanawa.order.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.clothsdanawa.common.security.CustomUserPrincipal;
import com.example.clothsdanawa.order.dto.OrderRequestDto;
import com.example.clothsdanawa.order.dto.OrderResponseDto;
import com.example.clothsdanawa.order.entity.OrderStatus;
import com.example.clothsdanawa.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
	private final OrderService orderService;

	// todo : 1. 주문 생성
	@PostMapping
	public ResponseEntity<OrderResponseDto> createOrder(
		@AuthenticationPrincipal CustomUserPrincipal userDetails,
		@RequestBody OrderRequestDto orderRequestDto
	) {
		return ResponseEntity.ok(orderService.createOrder(userDetails.getUserId(), orderRequestDto));
	}

	// todo : 2. 주문 조회
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderResponseDto> findOrder(@PathVariable Long orderId) {
		return ResponseEntity.ok(orderService.findOrder(orderId));
	}

	// todo : 3. 주문 상태 변경
	@PatchMapping("/{orderId}")
	public ResponseEntity<Map<String, String>> updateStatus(
		@PathVariable Long orderId,
		@RequestParam OrderStatus status
	) {
		String message = orderService.updateStatus(orderId, status);
		return ResponseEntity.ok(Map.of("message", message));
	}

	// todo : 4. 주문 요청 취소
	@DeleteMapping("/{orderId}")
	public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
		String message = orderService.cancelOrder(orderId);
		return ResponseEntity.ok(message);
	}
}

