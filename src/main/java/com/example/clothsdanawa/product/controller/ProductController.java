package com.example.clothsdanawa.product.controller;

import com.example.clothsdanawa.product.dto.request.ProductCreateRequest;
import com.example.clothsdanawa.product.dto.request.ProductStockRequest;
import com.example.clothsdanawa.product.dto.request.ProductUpdateRequest;
import com.example.clothsdanawa.product.dto.response.ProductCreateResponse;
import com.example.clothsdanawa.product.dto.response.ProductResponse;
import com.example.clothsdanawa.product.dto.response.ProductStockResponse;
import com.example.clothsdanawa.product.dto.response.ProductUpdateResponse;
import com.example.clothsdanawa.product.entity.Product;
import com.example.clothsdanawa.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
	public ProductCreateResponse createProduct(@RequestBody ProductCreateRequest request) {
		Long productId = productService.createProduct(
			request.getStoreId(),
			request.getProductName(),
			request.getPrice(),
			request.getStock()
		);
		return new ProductCreateResponse(productId);
	}

	/**
	 * 상품 수정 API
	 */
	@PutMapping("/{productId}")
	public ProductUpdateResponse updateProduct(@PathVariable Long productId,
		@RequestBody ProductUpdateRequest request) {
		productService.updateProduct(
			productId,
			request.getProductName(),
			request.getPrice(),
			request.getStock()
		);
		return new ProductUpdateResponse("상품이 수정되었습니다.");
	}

	/**
	 * 상품 삭제 API
	 */
	@DeleteMapping("/{productId}")
	public ProductUpdateResponse deleteProduct(@PathVariable Long productId) {
		productService.deleteProduct(productId);
		return new ProductUpdateResponse("상품이 삭제되었습니다.");
	}

	/**
	 * 상품 전체 조회 API
	 */
	@GetMapping
	public List<ProductResponse> getAllProducts() {
		return productService.findAllProducts().stream()
			.map(ProductResponse::new)
			.collect(Collectors.toList());
	}

	/**
	 * 상품 단일 조회 API
	 */
	@GetMapping("/{productId}")
	public ProductResponse getProduct(@PathVariable Long productId) {
		return productService.findProductById(productId);
	}

	/**
	 * 상품 재고 차감 API
	 * 수량만큼 재고를 줄이고 낙관적 락(@Version)으로 충돌을 제어
	 */
	@PatchMapping("/{productId}/stock")
	public ProductStockResponse decreaseStock(@PathVariable Long productId,
		@RequestBody ProductStockRequest request) {
		Product updatedProduct = productService.decreaseStock(productId, request.getQuantity());
		return new ProductStockResponse(updatedProduct);
	}

}
