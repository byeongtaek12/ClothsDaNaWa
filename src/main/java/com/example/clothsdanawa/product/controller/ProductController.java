package com.example.clothsdanawa.product.controller;

import com.example.clothsdanawa.product.dto.request.ProductCreateRequest;
import com.example.clothsdanawa.product.dto.request.ProductUpdateRequest;
import com.example.clothsdanawa.product.entity.Product;
import com.example.clothsdanawa.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 상품 API controller 클래스
 * 상품 등록, 수정, 삭제, 조회, 재고 차감 API
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;

	/**
	 * 상품 등록 API
	 */
	@PostMapping
	public Long createProduct(@RequestBody ProductCreateRequest request) {
		return productService.createProduct(
			request.getStoreId(),
			request.getProductName(),
			request.getPrice(),
			request.getStock()
		);
	}

	/**
	 * 상품 수정 API
	 */
	@PutMapping("/{productId}")
	public void updateProduct(@PathVariable Long productId,
		@RequestBody ProductUpdateRequest request) {
		productService.updateProduct(
			productId,
			request.getProductName(),
			request.getPrice(),
			request.getStock()
		);
	}

	/**
	 * 상품 삭제 API
	 */
	@DeleteMapping("/{productId}")
	public void deleteProduct(@PathVariable Long productId) {
		productService.deleteProduct(productId);
	}

	/**
	 * 상품 전체 조회 API
	 */
	@GetMapping
	public List<Product> getAllProducts() {
		return productService.findAllProducts();
	}

	/**
	 * 상품 단일 조회 API
	 */
	@GetMapping("/{productId}")
	public Product getProduct(@PathVariable Long productId) {
		return productService.findProductById(productId);
	}

	/**
	 * 상품 재고 차감 API
	 * 수량만큼 재고를 줄이고 낙관적 락(@Version)으로 충돌을 제어
	 */
	@PatchMapping("/{productId}/stock")
	public void decreaseStock(@PathVariable Long productId,
		@RequestParam int quantity) {
		productService.decreaseStock(productId, quantity);
	}
}
