package com.example.clothsdanawa.product;

import com.example.clothsdanawa.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
