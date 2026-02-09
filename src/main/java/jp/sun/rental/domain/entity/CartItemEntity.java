package jp.sun.rental.domain.entity;

import lombok.Data;

@Data
public class CartItemEntity {

	private int cartItemId;
	
	private int itemId;
	
	CartEntity cartEntity;
}
