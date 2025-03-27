package com.janta.billing.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class BasicAuthSecurityConfiguration {

	
		// Disabling csrf for put and post request as our application is stateless
		@Bean
		SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
			http.authorizeHttpRequests(auth->{
				auth.requestMatchers("/register", "/admin/product/**").permitAll()
						.anyRequest().authenticated();
			});
			http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
			http.httpBasic();
			http.csrf().disable();
			http.headers().frameOptions().sameOrigin();
			return http.build();
		}

	// Allow request from CORS This is global configuration 
	// for specific controller we can use @CrossOrigin annotation
		@Bean
		public WebMvcConfigurer corsConfigurer() {
			return new WebMvcConfigurer() {
				public void addCorsMappings(CorsRegistry registry) {
					registry.addMapping("/**")
					.allowedMethods("*")
					.allowedOrigins("*");
				}
			};
		}
	
}
