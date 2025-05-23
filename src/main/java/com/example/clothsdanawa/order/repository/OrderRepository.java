package com.example.clothsdanawa.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.clothsdanawa.cart.entity.Cart;
import com.example.clothsdanawa.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("SELECT c FROM Order c WHERE c.user.userId=:userId")
	Optional<Order> findByUserId(@Param("userId") Long userId);

	// Optional<Order> findOrderByCartId(Long cartId);

	// List<Order> findAllByCart(Cart cart);

}
