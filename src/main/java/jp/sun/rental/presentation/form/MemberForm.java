package jp.sun.rental.presentation.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class MemberForm implements Serializable{
	
	private int userId;
	
	private int card;
	
	private int userPoint;
	
	private String address;
	
	private String post;
	
	private String plan;
	
}
