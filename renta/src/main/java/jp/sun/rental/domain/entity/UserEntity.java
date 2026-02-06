package jp.sun.rental.domain.entity;

import lombok.Data;

@Data
public class UserEntity {

	private int userId;
	
	private String userName;
	
	private String password;
	
	private String email;
	
	private String tell;
	
	private String authority;
	
	private MemberEntity members;
}
