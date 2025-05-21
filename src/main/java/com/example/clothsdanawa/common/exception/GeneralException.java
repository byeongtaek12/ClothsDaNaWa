package com.example.clothsdanawa.common.exception;

import lombok.Getter;

/**
 * 비즈니스 로직에서 사용하는 커스텀 예외 클래스
 * ErrorCode 기반으로 메시지를 전달하고 공통 응답 처리에 사용됨
 */
@Getter
public class GeneralException extends RuntimeException {

	private final ErrorCode errorCode;

	public GeneralException(ErrorCode errorCode) {
		super(errorCode.getMessage());  // 예외 메시지는 ErrorCode의 메시지 사용
		this.errorCode = errorCode;
	}
}
