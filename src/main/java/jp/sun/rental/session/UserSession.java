package jp.sun.rental.session;

import java.io.Serializable;

public class UserSession implements Serializable {

	private static final long serialVersionUID = 1L;


	private String userId;
	private String userName;
	private String authority;

	public UserSession(String userId, String userName, String authority) {
		this.userId = userId;
		this.userName = userName;
		this.authority = authority;
	}
	public UserSession() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	// getter / setter
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String role) {
		this.authority = role;
	}

}
