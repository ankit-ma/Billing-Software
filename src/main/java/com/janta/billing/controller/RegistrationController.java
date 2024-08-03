package com.janta.billing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.janta.billing.dto.ApiResponse;
import com.janta.billing.dto.EmployeeDto;
import com.janta.billing.service.RegistrationService;

import jakarta.validation.Valid;

@RestController
public class RegistrationController {
	static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
	
	private RegistrationService registrationService;
	public RegistrationController(RegistrationService registrationService) {
		this.registrationService=registrationService;
	}

	@PostMapping("/register")
	public ApiResponse<EmployeeDto> register(@RequestBody @Valid EmployeeDto employee) {
		logger.error(employee.toString());
		
		//this.registrationService.registerAnEmployee(employee)
		return ApiResponse.<EmployeeDto>builder()
				.message(this.registrationService.registerAnEmployee(employee))
				.data(employee)
				.build();
	}
	
	@GetMapping("/hello")
	public String getHello() {
		return "Hello World";
	}

}
