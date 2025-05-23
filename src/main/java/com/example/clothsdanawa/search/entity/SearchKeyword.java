package com.example.clothsdanawa.search.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "search")
public class SearchKeyword {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String keyword;

	@Column(nullable = false)
	private int score;

	public void incrementScore(int value) {
		this.score += value;
	}

	public static SearchKeyword of(String keyword) {
		SearchKeyword searchKeyword = new SearchKeyword();
		searchKeyword.keyword = keyword;
		searchKeyword.score = 1;
		return searchKeyword;
	}
}
