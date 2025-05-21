package com.example.clothsdanawa.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 시스템 전역에서 사용될 에러 코드 Enum
 * 각 비즈니스 도메인의 예외 상황을 구분하고 일관된 메시지를 제공
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	// product
	PRODUCT_NOT_FOUND("PRODUCT_001", "해당 상품을 찾을 수 없습니다."),
	OUT_OF_STOCK("stock_001", "재고가 부족합니다."),
	// 이미 삭제된 상품입니다 추가

	// store (product에 필요 store에서 가져다 쓰시는거 권장)
	STORE_NOT_FOUND("STORE_001", "해당 스토어가 존재하지 않습니다."),

	// 공통 예외 (추가 가능)
	INVALID_INPUT_VALUE("COMMON_001", "잘못된 입력값입니다."), // 코드 정해주세요
	INTERNAL_SERVER_ERROR("COMMON_999", "서버 내부 오류가 발생했습니다.");

	private final String code;      // 에러 코드
	private final String message;   // 사용자에게 보여줄 메시지
}
