package com.example.clothsdanawa.cart.repository;

import com.example.clothsdanawa.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Optional<Cart> findByUserId(Long userId);
}
