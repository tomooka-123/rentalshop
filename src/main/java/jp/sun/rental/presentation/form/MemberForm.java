package jp.sun.rental.presentation.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class MemberForm implements Serializable {

	private String userId;
	
	private String card;
	
	private String userPoint;
	
	private String address;
	
	private String post;
	
	private String plan;
	
	private String name;
}
