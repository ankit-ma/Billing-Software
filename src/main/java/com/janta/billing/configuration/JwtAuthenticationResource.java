package com.janta.billing.configuration;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.janta.billing.dto.JWTAuthResponse;
import com.janta.billing.entity.EmployeeDetails;
import com.janta.billing.service.ActivityMappingService;
import com.janta.billing.service.RegistrationService;

@RestController
public class JwtAuthenticationResource {
	
	private JwtEncoder jwtEncoder;
	private RegistrationService registrationService;
	private ActivityMappingService activityMappingService;
	
	public JwtAuthenticationResource(JwtEncoder jwtEncoder,ActivityMappingService activityMappingService,RegistrationService registrationService) {
		this.jwtEncoder = jwtEncoder;
		this.activityMappingService = activityMappingService;
		this.registrationService = registrationService;
	}
	
	@PostMapping("/authenticate") 
	public JWTAuthResponse<List<?>> authenticate(Authentication authentication) {
		
		String authorisedRoleString = authentication.getAuthorities().stream().findFirst().get().getAuthority();
		List<String> roleActivityMappings = activityMappingService.getActivityMappingListWithRole(authorisedRoleString);
		EmployeeDetails employeeDetails = registrationService.fetchEmployeeDetails(authentication.getName());
		return JWTAuthResponse
				.<List<?>>builder()
				.email(authentication.getName())
				.token(createToken(authentication))
				.role(authentication.getAuthorities())
				.name(employeeDetails.getEmployeeName())
				.data(roleActivityMappings)
				.employeeId(employeeDetails.getId())
				.build();
		
	}
//	public Authentication authentication(Authentication authentication) {
//		return authentication;
//	}
	private String createToken(Authentication authentication) {
		var claims = JwtClaimsSet.builder()
								.issuer("self")
								.issuedAt(Instant.now())
								.expiresAt(Instant.now().plusSeconds(60 * 30))
								.subject(authentication.getName())
								.claim("scope", createScope(authentication))
								.build();
		
		return jwtEncoder.encode(JwtEncoderParameters.from(claims))
							.getTokenValue();
	}

	private String createScope(Authentication authentication) {
		return authentication.getAuthorities().stream()
			.map(a -> a.getAuthority())
			.collect(Collectors.joining(" "));			
	}

}

