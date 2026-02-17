package jp.sun.rental.presentation.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class RentalItemForm implements Serializable {

	private String rentalItemId;
	
	private String rentalId;
	
	private String itemId;
	
	private boolean returnFlag;
	
	private boolean deleteFlag;
	
	private ItemForm itemForm;
}
