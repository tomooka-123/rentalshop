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
				.requestMatchers("/user/update").authenticated()   
				.requestMatchers("/cart/**").authenticated()   
				.requestMatchers("/login").permitAll()
				.requestMatchers("/admin/**").hasAuthority("EMPLOYEE")
				.requestMatchers("/access-denied").permitAll()
				.anyRequest().permitAll());
		
		http.formLogin(login -> login
				.defaultSuccessUrl("/top")
				.loginPage("/login")
				.failureUrl("/login?error")
				.permitAll());
		
		http.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/top")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.permitAll());
		
		http.exceptionHandling(ex -> ex
	            .accessDeniedHandler((request, response, accessDeniedException) -> {
	                response.sendRedirect("/access-denied");
	            })
	        );
		
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
	
	/*//パスワードをハッシュ化する際に使用
	@Bean
	protected BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;
	}
	*/

}
