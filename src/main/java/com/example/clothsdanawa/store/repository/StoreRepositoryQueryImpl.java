package com.example.clothsdanawa.store.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.clothsdanawa.store.common.StoreStatus;
import com.example.clothsdanawa.store.dto.request.StoreFilterRequestDto;
import com.example.clothsdanawa.store.entity.QStore;
import com.example.clothsdanawa.store.entity.Store;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StoreRepositoryQueryImpl implements StoreRepositoryQuery {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Store> findStoresByKeywordWithCursorOrderByUpdatedAt(StoreFilterRequestDto requestDto) {
		QStore store = QStore.store;

		return jpaQueryFactory
			.selectFrom(store)
			.where(
				store.storeStatus.eq(StoreStatus.OPEN),                // 상태가 OPEN인 가게
				keywordContains(requestDto.getKeyword()),            // 키워드 포함 여부
				cursorCondition(requestDto.getCursor())                // 커서 필터
			)
			.orderBy(store.createdAt.asc())                            // 업데이트 날짜 기준 내림차순 정렬
			.limit(10)                                                // 커서 사이즈 10개
			.fetch();
	}

	// 키워드 조건 (company 필드에 키워드 포함)
	private BooleanExpression keywordContains(String keyword) {
		return (keyword != null && !keyword.isEmpty()) ? QStore.store.company.contains(keyword) : null;
	}

	// 커서 조건 (커서 이후 데이터를 조회)
	private BooleanExpression cursorCondition(Long cursor) {
		return (cursor != null) ? QStore.store.storeId.gt(cursor) : null;
	}
}
