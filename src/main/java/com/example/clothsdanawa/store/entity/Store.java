package com.example.clothsdanawa.store.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Store {
    @Id
    private Long Id;

    private String company;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnumType Status;
}
