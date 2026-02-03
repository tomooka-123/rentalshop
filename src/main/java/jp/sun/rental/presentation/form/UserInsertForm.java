package jp.sun.rental.presentation.form;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserInsertForm implements Serializable {
	
	private String userId;
	
	@NotEmpty
	private String userName;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String email;
	
	@NotEmpty
	private String tell;
	
	@NotEmpty
	private String post;
	
	@NotEmpty
	private String address;
	
	private String plan;
	
	@NotEmpty
	private String card;	
	
	@NotEmpty
	private String password;
	
	private String authority;
	
	private String userPoint;
	
	//private MemberEntity members;
}
