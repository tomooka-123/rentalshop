package jp.sun.rental.domain.entity;

import lombok.Data;

@Data
public class MemberEntity {

	private int userId;
	
	private int card;
	
	private int userPoint;
	
	private String address;
	
	private String post;
	
	private String plan;
	
	private String name;
}
