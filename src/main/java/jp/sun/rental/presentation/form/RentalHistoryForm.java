package jp.sun.rental.presentation.form;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class RentalHistoryForm implements Serializable {

	private String rentalId;
	
	private String userId;
	
	private String rentalDate;
	
	private String address;
	
	private String addressName;
	
	private List<RentalItemForm> rentalItems;
}
