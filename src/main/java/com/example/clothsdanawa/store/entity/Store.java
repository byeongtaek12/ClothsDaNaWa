package com.example.clothsdanawa.store.entity;

import com.example.clothsdanawa.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

// 테스트용 엔티티 지우셈

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Store extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private String company;

	private String status;
	private String storeNumber;
	private String address;
}
