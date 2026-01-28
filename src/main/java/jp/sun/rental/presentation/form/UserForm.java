package jp.sun.rental.presentation.form;

import lombok.Data;

@Data
public class UserForm {

	private String userId;
	
	private String userName;
	
	private String password;
	
	private String email;
	
	private String authority;
}
