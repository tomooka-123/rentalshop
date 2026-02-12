package jp.sun.rental.domain.entity;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class RentalHistoryEntity {

	private int rentalId;
	
	private int userId;
	
	private Date rentalDate;
	
	private List<RentalItemEntity> rentalItems;
}
