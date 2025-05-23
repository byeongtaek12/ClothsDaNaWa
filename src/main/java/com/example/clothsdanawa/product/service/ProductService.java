package com.example.clothsdanawa.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.clothsdanawa.common.exception.ErrorCode;
import com.example.clothsdanawa.common.exception.GeneralException;
import com.example.clothsdanawa.product.dto.response.ProductResponse;
import com.example.clothsdanawa.product.entity.Product;
import com.example.clothsdanawa.product.repository.ProductRepository;
import com.example.clothsdanawa.store.entity.Store;
import com.example.clothsdanawa.store.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

/**
 * 상품 관련 비즈니스 로직을 담당하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final StoreRepository storeRepository;

	/**
	 * 상품 등록
	 */
	public Long createProduct(Long storeId, String productName, int price, int stock) {
		Store store = storeRepository.findById(storeId)
			.orElseThrow(() -> new GeneralException(ErrorCode.STORE_NOT_FOUND));
		Product product = new Product(store, productName, price, stock);
		return productRepository.save(product).getId();
	}

	/**
	 * 상품 단건 조회 (내부용)
	 */
	private Product getProductEntityById(Long productId) {
		return productRepository.findById(productId)
			.orElseThrow(() -> new GeneralException(ErrorCode.PRODUCT_NOT_FOUND));
	}

	/**
	 * 상품 수정
	 */
	@Transactional
	public void updateProduct(Long productId, String productName, int price, int stock) {
		Product product = getProductEntityById(productId);
		product.update(productName, price, stock);
	}

	/**
	 * 상품 삭제 (soft delete)
	 */
	@Transactional
	public void deleteProduct(Long productId) {
		Product product = getProductEntityById(productId);
		product.markAsDeleted();
	}

	/**
	 * 전체 상품 조회
	 */
	@Transactional(readOnly = true)
	public List<Product> findAllProducts() {
		return productRepository.findAll(); // soft delete 제외됨
	}

	/**
	 * 재고 차감
	 */
	@Transactional
	public Product decreaseStock(Long productId, int quantity) {
		Product product = getProductEntityById(productId);
		product.decreaseStock(quantity);
		return product;
	}

	/**
	 * 상품 단건 조회 (DTO 변환)
	 */
	public ProductResponse findProductById(Long productId) {
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new GeneralException(ErrorCode.PRODUCT_NOT_FOUND));
		return new ProductResponse(product);
	}

}
