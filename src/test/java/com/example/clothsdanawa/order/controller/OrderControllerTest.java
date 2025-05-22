package com.example.clothsdanawa.order.controller;

import com.example.clothsdanawa.order.dto.OrderRequestDto;
import com.example.clothsdanawa.order.dto.OrderResponseDto;
import com.example.clothsdanawa.order.entity.OrderStatus;
import com.example.clothsdanawa.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

	private MockMvc mockMvc;

	@Mock
	private OrderService orderService;

	@InjectMocks
	private OrderController orderController;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
	}

	// @Test
	// void order_생성에_성공한다() throws Exception {
	// 	// given
	// 	OrderRequestDto requestDto = new OrderRequestDto(100L);
	// 	LocalDateTime now = LocalDateTime.of(2024, 1, 1, 12, 0);
	//
	// 	OrderResponseDto responseDto = new OrderResponseDto(
	// 		1L, 1L, 5L, null, 5000, 100L, now, OrderStatus.WAITING);
	//
	// 	when(orderService.createOrder(any(Long.class), any(OrderRequestDto.class))).thenReturn(responseDto);
	//
	// 	// when & then
	// 	mockMvc.perform(post("/orders/{cartId}", 1L)
	// 			.contentType(MediaType.APPLICATION_JSON)
	// 			.content(objectMapper.writeValueAsString(requestDto)))
	// 		.andExpect(status().isOk())
	// 		.andExpect(jsonPath("$.orderId").value(1L))
	// 		.andExpect(jsonPath("$.cartId").value(1L))
	// 		.andExpect(jsonPath("$.quantity").value(5L))
	// 		.andExpect(jsonPath("$.totalPrice").value(5000))
	// 		.andExpect(jsonPath("$.point").value(100L))
	// 		.andExpect(jsonPath("$.orderStatus").value("WAITING"));
	// }
	//
	// @Test
	// void 주문_상태_변경에_성공한다() throws Exception {
	// 	// given
	// 	Long orderId = 1L;
	// 	OrderStatus status = OrderStatus.IN_DELIVERY;
	// 	String message = "배송 중입니다.";
	//
	// 	when(orderService.updateStatus(orderId, status)).thenReturn(message);
	//
	// 	// when & then
	// 	mockMvc.perform(patch("/orders/{orderId}/status", orderId)
	// 			.param("status", status.name())
	// 			.contentType(MediaType.APPLICATION_JSON))
	// 		.andExpect(status().isOk())
	// 		.andExpect(jsonPath("$.message").value(message));
	// }
	//
	// @Test
	// void 주문_조회에_성공한다() throws Exception {
	// 	// given
	// 	Long orderId = 1L;
	// 	LocalDateTime now = LocalDateTime.of(2024, 1, 1, 12, 0);
	//
	// 	OrderResponseDto responseDto = new OrderResponseDto(
	// 		orderId, 1L, 5L, null, 5000, now, OrderStatus.WAITING);
	//
	// 	when(orderService.findOrder(orderId)).thenReturn(responseDto);
	//
	// 	// when & then
	// 	mockMvc.perform(get("/orders/{orderId}", orderId)
	// 			.contentType(MediaType.APPLICATION_JSON))
	// 		.andExpect(status().isOk())
	// 		.andExpect(jsonPath("$.orderId").value(orderId))
	// 		.andExpect(jsonPath("$.cartId").value(1L))
	// 		.andExpect(jsonPath("$.quantity").value(5L))
	// 		.andExpect(jsonPath("$.totalPrice").value(5000))
	// 		.andExpect(jsonPath("$.point").value(100L))
	// 		.andExpect(jsonPath("$.orderStatus").value("WAITING"));
	// }
	//
	// @Test
	// void 주문_취소에_성공한다() throws Exception {
	// 	// given
	// 	Long orderId = 1L;
	// 	String message = "주문이 취소되었습니다.";
	//
	// 	when(orderService.cancelOrder(orderId)).thenReturn(message);
	//
	// 	// when & then
	// 	mockMvc.perform(delete("/orders/{orderId}", orderId))
	// 		.andExpect(status().isOk())
	// 		.andExpect(jsonPath("$.message").value(message));
	// }
}
