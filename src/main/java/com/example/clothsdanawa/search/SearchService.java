package com.example.clothsdanawa.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.clothsdanawa.product.entity.Product;
import com.example.clothsdanawa.product.service.ProductService;
import com.example.clothsdanawa.search.dto.SearchResponseDto;
import com.example.clothsdanawa.store.entity.Store;
import com.example.clothsdanawa.store.service.StoreService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {

	private final StoreService storeService;
	private final ProductService productService;

	public List<SearchResponseDto> searchAll(String keyword) {

		List<Store> stores = storeService.searchByKeyword(keyword);
		List<Product> products = productService.searchByKeyword(keyword);

		List<SearchResponseDto> result = stores.stream().map(SearchResponseDto::from).collect(Collectors.toList());
		List<SearchResponseDto> result2 = products.stream().map(SearchResponseDto::from).collect(Collectors.toList());

		result.addAll(result2);

		return result;
	}
}
