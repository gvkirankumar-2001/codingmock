package com.fmc.mocktest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public UserDetailsManager details() {
		UserDetails user1 = User.builder().username("ravi").password(passwordEncoder().encode("ravi123")).build();
		UserDetails user2 = User.builder().username("raju").password(passwordEncoder().encode("raju123")).build();
		return new InMemoryUserDetailsManager(user1, user2);
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		security.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.GET, "/api/mock/**")
						.permitAll().requestMatchers("/swagger-ui/**", "/v3/api/**", "/swagger-ui.html").permitAll())
				.httpBasic(Customizer.withDefaults());
		return security.build();
	}
}
