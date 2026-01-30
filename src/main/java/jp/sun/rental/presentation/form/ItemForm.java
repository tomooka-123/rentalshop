package jp.sun.rental.presentation.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class ItemForm implements Serializable {

	private String itemId;
	
	private String itemName;
	
	private String genreId;
	
	private String itemImg;
	
	private String itemUpdate;
	
	private String artist;
	
	private String director;
	
	private String itemPoint;
}
