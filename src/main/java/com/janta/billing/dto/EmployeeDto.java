package com.janta.billing.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmployeeDto {
	
	@NotBlank(message="Employee name cannot be empty")
	String employeeName;
	@NotBlank(message="Phone number cannot be empty")
	String phoneNumber;
	String password;
	String email;
	String designation;
	
}
