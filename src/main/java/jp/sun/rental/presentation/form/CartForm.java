package jp.sun.rental.presentation.form;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CartForm implements Serializable {

	private String cartId;
	
	private String userId;
	
	private List<CartItemForm> cartItems;
}
