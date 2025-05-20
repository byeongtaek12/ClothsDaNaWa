package com.example.clothsdanawa.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.clothsdanawa.cart.entity.Cart;
import com.example.clothsdanawa.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	Optional<Order> findOrderByCartID(Long cartId);

	List<Order> findAllByCart(Cart cart);

}
