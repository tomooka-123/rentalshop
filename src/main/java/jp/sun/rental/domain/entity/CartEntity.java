package jp.sun.rental.domain.entity;

import lombok.Data;

@Data
public class CartEntity {

	private int cartId;
	
	private int userId;
	
	private CartItemEntity cartItems;
}
