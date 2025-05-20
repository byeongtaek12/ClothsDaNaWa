package com.example.clothsdanawa.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	// 공통 관련 오류

	// 사용자 관련 오류
	NOT_FOUND_USER_BY_EMAIL(HttpStatus.NOT_FOUND, "U001", "이메일을 가진 사용자를 찾을 수 없습니다."),
	NOT_FOUND_USER_BY_ID(HttpStatus.NOT_FOUND, "U002", "아이디를 가진 사용자를 찾을 수 없습니다."),
	CONFLICT_EMAIL(HttpStatus.CONFLICT, "U003", "이미 존재하는 이메일입니다"),
	BAD_REQUEST_PASSWORD(HttpStatus.BAD_REQUEST, "U004", "패스워드가 틀렸습니다");

	private final HttpStatus httpStatus;
	private final String errorCode;
	private final String message;
}
