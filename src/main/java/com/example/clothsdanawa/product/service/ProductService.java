package com.example.clothsdanawa.product.service;

import com.example.clothsdanawa.product.dto.response.ProductResponse;
import com.example.clothsdanawa.product.entity.Product;
import com.example.clothsdanawa.product.repository.ProductRepository;
import com.example.clothsdanawa.store.entity.Store;
import com.example.clothsdanawa.store.repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 상품 관련 비즈니스 로직을 담당하는 서비스 클래스
 * 상품 등록, 수정, 삭제, 조회, 재고 감소 처리
 */
@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final StoreRepository storeRepository;

	/**
	 * 상품 등록
	 * @param storeId 연관된 쇼핑몰 ID
	 * @param productName 상품 이름
	 * @param price 가격
	 * @param stock 재고 수량
	 * @return 저장된 상품 ID
	 */
	public Long createProduct(Long storeId, String productName, int price, int stock) {
		Store store = storeRepository.findById(storeId)
			.orElseThrow(() -> new EntityNotFoundException("해당 스토어가 존재하지 않습니다."));
		Product product = new Product(store, productName, price, stock);
		return productRepository.save(product).getId();
	}

	/**
	 * 상품 ID로 상품 엔티티 조회 (내부 전용)
	 */
	private Product getProductEntityById(Long productId) {
		return productRepository.findById(productId)
			.orElseThrow(() -> new EntityNotFoundException("해당 상품을 찾을 수 없습니다."));
	}

	/**
	 * 상품 정보 수정
	 */
	@Transactional
	public void updateProduct(Long productId, String productName, int price, int stock) {
		Product product = getProductEntityById(productId); // 엔티티 반환 받기
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
	 * 상품 목록 전체 조회
	 */
	@Transactional(readOnly = true)
	public List<Product> findAllProducts() {
		return productRepository.findAll(); // @Where로 soft delete 제외됨
	}

	/**
	 * 재고 차감 (낙관적 락 기반)
	 */
	@Transactional
	public Product decreaseStock(Long productId, int quantity) {
		Product product = getProductEntityById(productId);
		product.decreaseStock(quantity);
		return product;
	}

	/**
	 * 상품 ID로 상품을 조회
	 */
	public ProductResponse findProductById(Long productId) {
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new EntityNotFoundException("해당 상품을 찾을 수 없습니다."));
		return new ProductResponse(product);
	}


}
