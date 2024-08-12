package com.janta.billing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.janta.billing.dto.ApiResponse;
import com.janta.billing.dto.EmployeeDto;
import com.janta.billing.exception.SystemException;
import com.janta.billing.service.EmployeeDetailsService;


@RestController
public class EmployeeDetailsController {

	@Autowired
	private EmployeeDetailsService employeeDetailsService;
	
	@GetMapping("/admin/employee/pending-approval")
	public ApiResponse<List<EmployeeDto>> getEmployeesWithPendingApprovals(){
		
		List<EmployeeDto> employeeDtos = employeeDetailsService.fetchEmployeesWithPendingApprovals();
		
		return ApiResponse.<List<EmployeeDto>>builder()
				.message("SUCCESS")
				.data(employeeDtos)
				.build();
		
	}
	
	@PutMapping("/admin/employee/pending-approval/{id}")
	public ApiResponse<String> updateApprovalStatus(@PathVariable Long id) throws SystemException{
		
	
			employeeDetailsService.updateApprovalStatus(id);
			return ApiResponse.<String> builder()
					.message("SUCCESS")
					.data("Approval Updated succesfully")
					.build();
		
		
	}
}
