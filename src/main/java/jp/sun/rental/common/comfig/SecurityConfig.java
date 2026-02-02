package jp.sun.rental.common.comfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("cart").authenticated());
		
		http.formLogin(login -> login
				.defaultSuccessUrl("/cart").permitAll());
		
		return http.build();
	}
}
