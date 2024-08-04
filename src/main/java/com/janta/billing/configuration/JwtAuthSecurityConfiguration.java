package com.janta.billing.configuration;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import jakarta.transaction.SystemException;
import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
public class JwtAuthSecurityConfiguration {

	
	 	@Bean
	    //authentication
	    public UserDetailsService userDetailsService() {

	        return new UserInfodetailsService();
	    }
		// Disabling csrf for put and post request as our application is stateless
		@Bean
		SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
			http.authorizeHttpRequests(auth->{
				auth.requestMatchers("/register").permitAll().anyRequest().authenticated();
			});
			http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
			http.httpBasic(withDefaults());
			http.formLogin(withDefaults());
			http.csrf().disable();
			http.headers().frameOptions().sameOrigin();
			http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
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
		
		 @Bean
		 public PasswordEncoder passwordEncoder() {
		        return new BCryptPasswordEncoder();
		    }

		    @Bean
		    public AuthenticationProvider authenticationProvider(){
		        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
		        authenticationProvider.setUserDetailsService(userDetailsService());
		        authenticationProvider.setPasswordEncoder(passwordEncoder());
		        return authenticationProvider;
		    }
	/*
	 * For a JWT to work we need to add following necessary methods
	 * 
	 *  1. generate keypair : KeyPair
	 *  2. nimbus RSA key object 
	 *  3. JWK source
	 */
		@Bean
		public KeyPair keyPair() throws SystemException {

			try {
				KeyPairGenerator keypairgenerator = KeyPairGenerator.getInstance("RSA");
				keypairgenerator.initialize(2048);
				return keypairgenerator.generateKeyPair();
			} catch (Exception e) {
				throw new SystemException(e.getMessage());
			}
		
		}
		@Bean
		public RSAKey rsaKey(KeyPair keyPair) {
			return new RSAKey
					.Builder((RSAPublicKey)keyPair.getPublic())
					.privateKey(keyPair.getPrivate())
					.keyID(UUID.randomUUID().toString()).build();
		}
		
		@Bean
		public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
			JWKSet jwkSet = new JWKSet(rsaKey);
			
			return (jwkSelector,context)-> jwkSelector.select(jwkSet);
			
		}
		@Bean
		public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
			return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
		}
		
		@Bean
		public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
			return new NimbusJwtEncoder(jwkSource);
		}
}
