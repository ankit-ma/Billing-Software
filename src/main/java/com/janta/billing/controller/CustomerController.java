package com.janta.billing.controller;

import com.janta.billing.dto.CustomerDetailsDashboardDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.janta.billing.dto.ApiResponse;
import com.janta.billing.dto.CustomerDetailsDto;
import com.janta.billing.entity.CustomerDetails;
import com.janta.billing.entity.EmployeeDetails;
import com.janta.billing.exception.SystemException;
import com.janta.billing.service.CustomerDetailsService;
import com.janta.billing.service.EmployeeDetailsService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/janta-store/add-customer")
public class CustomerController {

	private static Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
	@Autowired
	private CustomerDetailsService customerDetailsService;
	@Autowired
	private EmployeeDetailsService employeeDetailsService;
	
	@PostMapping("/{employeeId}")
	public ApiResponse<?> saveCustomerDetails(@PathVariable Long employeeId,@Valid @RequestBody CustomerDetailsDto customerDetailsDto){
		try {
		EmployeeDetails employeeDetails = employeeDetailsService.getEmployeeDetailsById(employeeId);
		if(employeeDetails == null) {
			throw new SystemException("Invalid employee user");
		}
		customerDetailsService.saveCustomerDetails(customerDetailsDto,employeeDetails);
		return ApiResponse.builder()
				.message("Customer Added succesfully").build();
		}
		catch(Exception e) {
			LOGGER.error(e.getMessage());
			throw new SystemException(e.getMessage());
		}
	}
	@GetMapping("/{employeeId}/{phoneNumber}")
	public ResponseEntity<CustomerDetails> getCustomerDetails(@PathVariable Long employeeId,@PathVariable String phoneNumber){
		try {
		EmployeeDetails employeeDetails = employeeDetailsService.getEmployeeDetailsById(employeeId);
		if(employeeDetails == null) {
			throw new SystemException("Invalid employee user");
		}
		CustomerDetails customerDetails= customerDetailsService.fetchCustomerDetails(phoneNumber);
		if(customerDetails == null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(null);
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(customerDetails);
		}
		catch(Exception e) {
			LOGGER.error(e.getMessage());
			throw new SystemException(e.getMessage());
		}
	}

	@GetMapping("dashboard/{size}/{pageNumber}")
	public ResponseEntity<?> getAllCustomerDetails(@PathVariable Integer pageNumber, @PathVariable Integer size){

		try{
			Map<String,Object> result = customerDetailsService.fetchCustomerDetailsDashboard(size,pageNumber);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
}
