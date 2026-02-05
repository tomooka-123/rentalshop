package jp.sun.rental.presentation.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserUpdateForm implements Serializable {
		
	// user
	private String user_id;
	
	private String userName;
	
	private String email;
	
	private String tell;
	
	private String password;
	
	// member
	private String address;
	
	private String post;
	
	private String plan;
	
}

