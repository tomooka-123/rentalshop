package jp.sun.rental.domain.entity;

import lombok.Data;

@Data
public class RentalAddressEntity {

	private int rentalID;
	
	private String address;
	
	private String name;
	
	private String tell;
}
