package com.example.clothsdanawa.common.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
		return ResponseEntity.status(404).body(e.getMessage());
	}

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<ErrorResponse> handleBaseException(BaseException e) {
		return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(ErrorResponse.from(e.getErrorCode()));
	}

	/**
	 * 비즈니스 로직에서 발생하는 일반 예외 처리
	 */
	@ExceptionHandler(GeneralException.class)
	public ResponseEntity<Map<String, Object>> handleGeneralException(GeneralException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("code", ex.getErrorCode().getErrorCode());
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
		body.put("code", ErrorCode.INTERNAL_SERVER_ERROR.getErrorCode());
		body.put("message", ErrorCode.INTERNAL_SERVER_ERROR.getMessage());

		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError)error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, errors);
		return ResponseEntity.status(400).body(response);
	}
}
