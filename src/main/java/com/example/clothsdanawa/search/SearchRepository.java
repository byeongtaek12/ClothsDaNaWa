package com.example.clothsdanawa.search;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.clothsdanawa.search.entity.SearchKeyword;

public interface SearchRepository extends JpaRepository<SearchKeyword, Long> {

}
