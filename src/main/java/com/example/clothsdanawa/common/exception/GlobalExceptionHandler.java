package com.example.clothsdanawa.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 전역 예외 처리 핸들러
 * 비즈니스 예외(GeneralException) 및 예상치 못한 예외를 처리
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 비즈니스 로직에서 발생하는 일반 예외 처리
	 */
	@ExceptionHandler(GeneralException.class)
	public ResponseEntity<Map<String, Object>> handleGeneralException(GeneralException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("code", ex.getErrorCode().getCode());
		body.put("message", ex.getErrorCode().getMessage());

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 시스템 내부 예외 처리 (같이 쓰셔도 됩니다)
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("code", ErrorCode.INTERNAL_SERVER_ERROR.getCode());
		body.put("message", ErrorCode.INTERNAL_SERVER_ERROR.getMessage());

		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
