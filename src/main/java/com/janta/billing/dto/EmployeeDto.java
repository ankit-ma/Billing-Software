package com.janta.billing.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
	
	
	Long id;
	
	@NotBlank(message="Employee name cannot be empty")
	String employeeName;
	@NotBlank(message="Phone number cannot be empty")
	String phoneNumber;

	String password;
	String email;
	String designation;
	Boolean isApproved;
	LocalDateTime loggedDate;
}
