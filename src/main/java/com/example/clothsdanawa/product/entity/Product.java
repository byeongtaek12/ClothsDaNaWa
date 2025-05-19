package com.example.clothsdanawa.product.entity;

import com.example.clothsdanawa.store.entity.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE product SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "store_id")
	private Store store;

	@Column(nullable = false)
	private String productName;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private int stock;

	@Version
	private Long version;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private LocalDateTime deletedAt;

	public Product(Store store, String productName, int price, int stock) {
		this.store = store;
		this.productName = productName;
		this.price = price;
		this.stock = stock;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public void update(String productName, int price, int stock) {
		this.productName = productName;
		this.price = price;
		this.stock = stock;
		this.updatedAt = LocalDateTime.now();
	}

	public void decreaseStock(int quantity) {
		if (this.stock < quantity) {
			throw new IllegalArgumentException("재고가 부족합니다.");
		}
		this.stock -= quantity;
		this.updatedAt = LocalDateTime.now();
	}

	public void markAsDeleted() {
		this.deletedAt = LocalDateTime.now();
	}
}
