package com.example.clothsdanawa.product.entity;

import com.example.clothsdanawa.store.entity.Store;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Product {

    @Id
    private Long id;

    @JoinColumn(name = "store_id")
    @ManyToOne
    private Store store;
    public String Name;

    private int price;

    private int stock;
}
