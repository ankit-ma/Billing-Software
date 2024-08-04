package com.janta.billing.service;

import com.janta.billing.dto.EmployeeDto;
import com.janta.billing.entity.EmployeeDetails;

public interface RegistrationService {
	public String registerAnEmployee(EmployeeDto employeeDto);
	public EmployeeDetails fetchEmployeeDetails(String email);
}
