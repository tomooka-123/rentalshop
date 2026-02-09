package jp.sun.rental.domain.entity;

import java.util.List;

import lombok.Data;

@Data
public class CartEntity {

	private int cartId;
	
	private int userId;
	
	private List<CartItemEntity> cartItems;
}
