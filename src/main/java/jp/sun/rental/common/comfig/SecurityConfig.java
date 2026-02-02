package jp.sun.rental.common.comfig;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	private DataSource dataSource;
	
	public SecurityConfig (DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("cart").authenticated());
		
		http.formLogin(login -> login
				.defaultSuccessUrl("/cart").permitAll());
		
		http.sessionManagement(session -> session
				.maximumSessions(1)
				.maxSessionsPreventsLogin(true));
		
		return http.build();
	}
	
	@Bean
	protected UserDetailsManager userDetailsManager() {
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(this.dataSource);
		
		return users;
	}
	
	@Bean
	protected HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}
}
