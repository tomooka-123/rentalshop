package jp.sun.rental.domain.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class ItemEntity {

	private int itemId;
	
	private String itemName;
	
	private int genreId;
	
	private String itemImg;
	
	private Date itemUpdate;
	
	private String artist;
	
	private String director;
	
	private int itemPoint;
	
}
