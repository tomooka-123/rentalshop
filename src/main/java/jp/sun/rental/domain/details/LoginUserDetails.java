package jp.sun.rental.domain.details;

import java.util.Collection;
import java.util.Collections;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jp.sun.rental.domain.entity.UserEntity;


//独自テーブルから特定の列要素を指定してユーザー名、パスワード、権限を取得する
//Spring BootテキストP349の図のUserDetailsの代わり
public class LoginUserDetails implements UserDetails {

	private UserEntity userEntity;
	
	public LoginUserDetails(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO 自動生成されたメソッド・スタブ
		Collection<GrantedAuthority> authority = Collections.singletonList(new SimpleGrantedAuthority(userEntity.getAuthority()));
		return authority;
	}

	@Override
	public @Nullable String getPassword() {
		// TODO 自動生成されたメソッド・スタブ
		return userEntity.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO 自動生成されたメソッド・スタブ
		return userEntity.getUserName();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}

}
