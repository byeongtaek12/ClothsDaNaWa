package com.example.clothsdanawa.cart.entity;

import com.example.clothsdanawa.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "cartItems")
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_id")
	private Cart cart;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(nullable = false)
	private int quantity;

	//금액계산
	public int getTotalPrice() {
		return product.getPrice() * quantity;
	}

	public void addQuantity(int quantity){
		this.quantity += quantity;
	}

	public static CartItem of(Cart cart,Product product,int quantity){
		CartItem cartItem=new CartItem();
		cartItem.cart=cart;
		cartItem.product=product;
		cartItem.quantity=quantity;
		return cartItem;
	}

	public void updateQuantity(int quantity) {
		this.quantity= quantity;
	}
}