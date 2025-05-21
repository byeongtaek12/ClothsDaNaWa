package com.example.clothsdanawa.cart.repository;

import com.example.clothsdanawa.cart.entity.Cart;
import com.example.clothsdanawa.common.exception.BaseException;
import com.example.clothsdanawa.common.exception.ErrorCode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

	@Query("SELECT c FROM Cart c WHERE c.user.userId=:userId")
	Optional<Cart> findByUserId(@Param("userId") Long userId);

	default Cart findByUserIdOrElseThrow(Long userId) {
		return findByUserId(userId).orElseThrow(() -> new BaseException(ErrorCode.CART_NOT_FOUND));
	}

	@Modifying
	@Transactional
	@Query("DELETE FROM Cart c WHERE c.user.userId = :userId")
	void deleteByUserId(@Param("userId") Long userId);
}
