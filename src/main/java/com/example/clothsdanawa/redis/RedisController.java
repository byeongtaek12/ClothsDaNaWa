package com.example.clothsdanawa.redis;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/keywords")
public class RedisController {

	private final RedisService redisService;

	@GetMapping
	public ResponseEntity<List<String>> getTop10Keywords() {
		List<String> top10Keywords = redisService.getTop10Keywords();
		return new ResponseEntity<>(top10Keywords, HttpStatus.OK);
	}
}
