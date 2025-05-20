package com.example.clothsdanawa.store.entity;

import org.hibernate.annotations.DynamicInsert;

import com.example.clothsdanawa.common.BaseEntity;
import com.example.clothsdanawa.store.dto.request.StoreCreateRequestDto;
import com.example.clothsdanawa.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Stores")
@DynamicInsert
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseEntity {
	/*
	  컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long storeId;
	private String company;
	@Enumerated(EnumType.STRING)
	private StoreStatus storeStatus;
	private String storeNumber;
	private String address;

	/*
	  생성자 - 약속된 형태로만 생성가능하도록 합니다.
	 */
	public Store(StoreCreateRequestDto requestDto) {
		this.company = requestDto.getCompany();
		this.storeStatus = StoreStatus.PENDING;
		this.storeNumber = requestDto.getStoreNumber();
		this.address = requestDto.getAddress();
	}

	/*
	  연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	// @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
	// private List<Product> product;

	/*
	  연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
	 */
	// public void addProduct(Product product) {
	// 	this.product.add(product);
	// 	product.setStore(this);
	// }

	/*
	  서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
	 */
	public void approveStore() {
		this.storeStatus = StoreStatus.OPEN;
	}

	public void closeStore() {
		this.storeStatus = StoreStatus.CLOSED;
	}
}
