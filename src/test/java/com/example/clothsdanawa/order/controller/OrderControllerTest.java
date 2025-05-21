package com.example.clothsdanawa.order.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;

import com.example.clothsdanawa.order.dto.OrderRequestDto;
import com.example.clothsdanawa.order.dto.OrderResponseDto;
import com.example.clothsdanawa.order.entity.Order;
import com.example.clothsdanawa.order.entity.OrderStatus;
import com.example.clothsdanawa.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private OrderService orderService;

	@InjectMocks
	private OrderController orderController;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		// orderController에 직접 orderService mock 주입
		ReflectionTestUtils.setField(orderController, "orderService", orderService);
	}

	@Test
	void order_생성에_성공한다() throws Exception {
		// given
		OrderRequestDto requestDto = new OrderRequestDto(100L);

		OrderResponseDto responseDto = new OrderResponseDto(
			1L, 1L, 5L, null, 5000, 100L, LocalDateTime.now(), OrderStatus.WAITING);

		when(orderService.createOrder(any(Long.class), any(OrderRequestDto.class))).thenReturn(responseDto);

		// when & then
		mockMvc.perform(post("/orders/{cartId}", 1L)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(requestDto)))
			.andExpect(status().isOk());
	}

	@Test
	void order_조회에_성공한다() throws Exception {
		// given
		Long orderId = 1L;
		OrderResponseDto responseDto = new OrderResponseDto(
			orderId, 1L, 5L, null, 5000, 100L, LocalDateTime.now(), OrderStatus.WAITING
		);

		when(orderService.findOrder(orderId)).thenReturn(responseDto);

		// when & then
		mockMvc.perform(get("/orders/{orderId}", orderId)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.orderId").value(orderId))
			.andExpect(jsonPath("$.cartId").value(1L))
			.andExpect(jsonPath("$.quantity").value(5L))
			.andExpect(jsonPath("$.totalPrice").value(5000))
			.andExpect(jsonPath("$.point").value(100L))
			.andExpect(jsonPath("$.orderStatus").value(OrderStatus.WAITING.name()));
	}

	@Test
	void 주문_상태_변경에_성공한다() throws Exception {
		// given
		Long orderId = 1L;
		OrderStatus newStatus = OrderStatus.IN_DELIVERY;
		String expectedMessage = "배송 중입니다.";

		when(orderService.updateStatus(orderId, newStatus)).thenReturn(expectedMessage);

		// when & then
		mockMvc.perform(patch("/orders/{orderId}/status", orderId)
				.param("status", newStatus.name())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(expectedMessage));
	}

	@Test
	void 주문_취소에_성공한다() throws Exception {
		// given
		Long orderId = 1L;
		String expectedMessage = "주문이 취소되었습니다.";

		when(orderService.cancelOrder(orderId)).thenReturn(expectedMessage);

		// when & then
		mockMvc.perform(delete("/orders/{orderId}", orderId))
			.andExpect(status().isOk())
			.andExpect(content().string(expectedMessage));
	}
}
