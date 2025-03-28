package com.janta.billing.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetailsDto {

	@NotBlank
	String customerName;
	@NotBlank
	String phoneNumber;
	@NotBlank
	String address;
	@NotBlank
	String email;
	
}
