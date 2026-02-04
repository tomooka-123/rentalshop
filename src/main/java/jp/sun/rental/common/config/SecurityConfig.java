package jp.sun.rental.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	//セキュリティ設定PasswordEncorderのNoOpPasswordEncordeは後にBCryptPasswordEncorderに変更予定
	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/search/user").authenticated()
				.anyRequest().permitAll());
		
		http.formLogin(login -> login
				.defaultSuccessUrl("/search/user")
				.permitAll());
		
		http.sessionManagement(session -> session
				.maximumSessions(1)
				.maxSessionsPreventsLogin(true));
		
		return http.build();
	}
	
	@Bean
	protected HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}
	
	@Bean
	@SuppressWarnings("deprecation")
	PasswordEncoder passwordEncoder() {
	    // パスワードをハッシュ化せずに、文字列をそのまま比較する設定
	    return NoOpPasswordEncoder.getInstance();
	}
}
