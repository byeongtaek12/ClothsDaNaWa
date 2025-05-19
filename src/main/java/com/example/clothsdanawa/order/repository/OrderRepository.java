package com.example.clothsdanawa.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.clothsdanawa.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
