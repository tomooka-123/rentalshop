package jp.sun.rental.presentation.form;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserInsertForm implements Serializable {
	
	private String userId;
	
	@NotEmpty(message = "ユーザー名は必須入力です")
	private String userName;
	
	@NotEmpty(message = "氏名は必須入力です")
	private String name;
	
	@NotEmpty(message = "メールアドレスは必須入力です")
	private String email;
	
	@NotEmpty(message = "電話番号は必須入力です")
	private String tell;
	
	@NotEmpty(message = "郵便番号は必須入力です")
	private String post;
	
	@NotEmpty(message = "住所は必須入力です")
	private String address;
	
	@NotEmpty(message = "会員プランを選んでください")
	private String plan;
	
	@NotEmpty(message = "クレジットカード番号は必須入力です")
	private String card;	
	
	@NotEmpty(message = "パスワードは必須入力です")
	private String password;
	
	private String authority;
	
	private String userPoint;
	
	//private MemberEntity members;
}
