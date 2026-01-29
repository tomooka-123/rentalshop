package jp.sun.rental.presentation.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserForm implements Serializable {

	private int userId;
	
	private String userName;
	
	private String password;
	
	private String email;
	
	private String tell;
	
	private String authority;
	
	private MemberForm members;
	
}
