package com.example.clothsdanawa.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 시스템 전역에서 사용될 에러 코드 Enum
 * 각 비즈니스 도메인의 예외 상황을 구분하고 일관된 메시지를 제공
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	// 공통 관련 오류

	// 사용자 관련 오류
	BAD_REQUEST_PASSWORD(HttpStatus.BAD_REQUEST, "U001", "패스워드가 틀렸습니다"),
	BAD_REQUEST_USER_ROLE(HttpStatus.BAD_REQUEST, "U002", "존재하지 않는 userRole 입니다"),
	NOT_FOUND_USER_BY_EMAIL(HttpStatus.NOT_FOUND, "U002", "이메일을 가진 사용자를 찾을 수 없습니다."),
	NOT_FOUND_USER_BY_ID(HttpStatus.NOT_FOUND, "U003", "아이디를 가진 사용자를 찾을 수 없습니다."),
	CONFLICT_EMAIL(HttpStatus.CONFLICT, "U004", "이미 존재하는 이메일입니다"),

	// product
	PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND,"PRODUCT_001", "해당 상품을 찾을 수 없습니다."),
	OUT_OF_STOCK(HttpStatus.BAD_REQUEST,"stock_001", "재고가 부족합니다."),
	// 이미 삭제된 상품입니다 추가

	// store (product에 필요 store에서 가져다 쓰시는거 권장)
	STORE_NOT_FOUND(HttpStatus.NOT_FOUND,"STORE_001", "해당 스토어가 존재하지 않습니다."),
	STORE_FORBIDDEN(HttpStatus.FORBIDDEN, "STORE_002", "스토어의 주인만 이용 가능합니다."),

	// 공통 예외 (추가 가능)
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST,"COMMON_001", "잘못된 입력값입니다."), // 코드 정해주세요
	INTERNAL_SERVER_ERROR(HttpStatus.BAD_REQUEST,"COMMON_999", "서버 내부 오류가 발생했습니다."),
	//cart
	CART_NOT_FOUND(HttpStatus.NOT_FOUND, "CART_001","장바구니가 없습니다."),
	CART_EMPTY(HttpStatus.NO_CONTENT, "CART_002","장바구니가 비어있습니다."),
	CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "CART_003","장바구니에 해당 상품이 없습니다."),
	CART_EXPIRED(HttpStatus.BAD_REQUEST, "CART_004","만료된 장바구니입니다."),
	CART_STORE_CLOSED(HttpStatus.BAD_REQUEST, "CART_005","운영중인 쇼핑몰이 아닙니다."),
	CART_QUANTITY_EXCEEDED(HttpStatus.BAD_REQUEST, "CART_006","요청 수량이 재고보다 많습니다.");

	private final HttpStatus httpStatus;
	private final String errorCode;
	private final String message;
}

//http 수정
