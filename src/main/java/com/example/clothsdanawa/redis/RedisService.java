package com.example.clothsdanawa.redis;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import com.example.clothsdanawa.search.SearchRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {

	private final RedisTemplate<String, String> redisTemplate;
	private final SearchRepository searchRepository;

	private String getTodayKey() {
		return "hot_keywords:" + LocalDate.now();
	}

	public void incrementCount(String keyword) {

		//입력된 값이 있는지 검증
		if (keyword == null || keyword.isBlank())
			return;

		//해당 키가 없을 경우에만 만료시간지정
		String redisKey = getTodayKey();
		if (!redisTemplate.hasKey(redisKey)) {
			redisTemplate.expire(redisKey, Duration.ofHours(30));
		}
		//입력된 keyword 값 +1
		//키워드 : score 값 저장
		redisTemplate.opsForZSet().incrementScore(redisKey, keyword, 1);

	}

	//인기검색어 top10 가져오기 //현재날짜기준
	public List<String> getTop10Keywords() {
		String redisKey = getTodayKey();

		Set<ZSetOperations.TypedTuple<String>> topKeywords = redisTemplate.opsForZSet()
			.reverseRangeWithScores(redisKey, 0, 9);
		
		if (topKeywords == null)
			return List.of();

		return topKeywords.stream()
			.map(rank -> rank.getValue() + " : " + rank.getScore() + "회")
			.toList();

	}
}
