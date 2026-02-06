package jp.sun.rental.presentation.form;

import java.io.Serializable;

import lombok.Data;

@Data

public class CartItemForm implements Serializable {

	private String cartItemId;
	
	private String cartId;
	
	private String itemId;
	
}
