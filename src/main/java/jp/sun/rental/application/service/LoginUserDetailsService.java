package jp.sun.rental.application.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.sun.rental.domain.details.LoginUserDetails;
import jp.sun.rental.domain.entity.UserEntity;
import jp.sun.rental.domain.repository.UserRepository;


//UserDetailsServiceインターフェースを実装した独自テーブルからユーザー情報を取得するサービス
//Spring BootテキストP349の図のJdbcUserDetailsManagerの代わり
@Service
public class LoginUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;
	
	public LoginUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO 自動生成されたメソッド・スタブ
		try {
			UserEntity userEntity = userRepository.getOnlyUserByName(username);
			
			if (userEntity == null) {
	            throw new UsernameNotFoundException("User not found with name: " + username);
	        }
			
			return new LoginUserDetails(userEntity);
		}catch (Exception e) {
			throw new UsernameNotFoundException("user not found");
		}
	}
}
