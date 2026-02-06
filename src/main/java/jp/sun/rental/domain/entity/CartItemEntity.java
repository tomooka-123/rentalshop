package jp.sun.rental.domain.entity;

import lombok.Data;

@Data
public class CartItemEntity {

	private int cartItemId;
	
	private int cartId;
	
	private int itemId;
}
