package com.janta.billing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.janta.billing.dto.ApiResponse;
import com.janta.billing.dto.CustomerDetailsDto;
import com.janta.billing.entity.EmployeeDetails;
import com.janta.billing.exception.SystemException;
import com.janta.billing.service.CustomerDetailsService;
import com.janta.billing.service.EmployeeDetailsService;

import jakarta.validation.Valid;

@RestController
public class CustomerController {

	private static Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
	@Autowired
	private CustomerDetailsService customerDetailsService;
	@Autowired
	private EmployeeDetailsService employeeDetailsService;
	
	@PostMapping("/janta-store/add-customer/{employeeId}")
	public ApiResponse<?> saveCustomerDetails(@PathVariable Long employeeId,@Valid @RequestBody CustomerDetailsDto customerDetailsDto){
		try {
		EmployeeDetails employeeDetails = employeeDetailsService.getEmployeeDetailsById(employeeId);
		if(employeeDetails == null) {
			throw new SystemException("Invalid employee user");
		}
		customerDetailsService.saveCustomerDetails(customerDetailsDto,employeeDetails);
		return ApiResponse.builder().build();
		}
		catch(Exception e) {
			LOGGER.error(e.getMessage());
			throw new SystemException(e.getMessage());
		}
	}
	
}
