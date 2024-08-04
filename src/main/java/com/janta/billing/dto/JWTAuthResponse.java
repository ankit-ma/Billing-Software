package com.janta.billing.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JWTAuthResponse<T> {

	private String token;
	private String name;
	private String email;
	private Long employeeId;
	private Collection<? extends GrantedAuthority> role;
	private T data;
}
