package jp.sun.rental.presentation.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserInsertForm implements Serializable {
	
	private String userId;
	
	private String userName;
	
	private String email;
	
	private String tell;
	
	private String post;
	
	private String address;
	
	private String plan;	
	
	private String card;	
	
	private String password;
	
	//private String authority;
	
	private String userPoint;
	
	//private MemberEntity members;
}
