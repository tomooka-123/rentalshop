package jp.sun.rental.domain.entity;

import lombok.Data;

@Data
public class UserUpdateEntity {
	
	// userEntity
	private int user_id;
	
	private String userName;
	
	private String email;
	
	private String tell;
	
	private String password;
	
	// memberEntity
	private String address;
	
	private String post;
	
	private String plan;
	
}
