package jp.sun.rental.domain.entity;

import lombok.Data;

@Data
public class MemberEntity {

	private int userId;
	
	private String card;
	
	private int userPoint;
	
	private String post;
	
	private String address;
	
	private String plan;
	
	private String name;
}
