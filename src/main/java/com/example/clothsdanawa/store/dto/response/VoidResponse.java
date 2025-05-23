package com.example.clothsdanawa.store.dto.response;

import com.example.clothsdanawa.store.entity.Store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class VoidResponse {
	private String message;

	public static VoidResponse from(String message) {
		return VoidResponse.builder()
			.message(message)
			.build();
	}
}
