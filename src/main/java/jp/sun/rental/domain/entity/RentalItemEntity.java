package jp.sun.rental.domain.entity;

import lombok.Data;

@Data
public class RentalItemEntity {

	private int retalItemId;
	
	private int rentalId;
	
	private int ItemId;
	
	private int returnFlag;
	
	private ItemEntity itemEntity;
}
