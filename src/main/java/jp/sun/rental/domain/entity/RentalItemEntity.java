package jp.sun.rental.domain.entity;

import lombok.Data;

@Data
public class RentalItemEntity {

	private int rentalItemId;
	
	private int rentalId;
	
	private int itemId;
	
	private boolean returnFlag;
	
	private ItemEntity itemEntity;
}
