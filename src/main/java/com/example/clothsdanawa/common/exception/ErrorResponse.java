package com.example.clothsdanawa.common.exception;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class ErrorResponse {
	private HttpStatus httpStatus;
	private String errorCode;
	private String message;

	public static ErrorResponse of(ErrorCode errorCode) {
		return ErrorResponse.builder()
			.httpStatus(errorCode.getHttpStatus())
			.errorCode(errorCode.getErrorCode())
			.message(errorCode.getMessage())
			.build();
	}
}
