package jp.sun.rental.presentation.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserUpdateForm implements Serializable {
		
	// userEntity
	private int user_id;
	
	private String userName;
		
	private String email;
	
	private String tell;
	
	private String password;
	
	private String passwordRefel;
	
	// memberEntity
	private String name;
	
	private String address;
	
	private String post;
	
	private String card;
	
	private String plan;
	
}

