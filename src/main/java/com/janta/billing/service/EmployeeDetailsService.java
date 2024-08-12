package com.janta.billing.service;

import java.util.List;

import com.janta.billing.dto.EmployeeDto;
import com.janta.billing.entity.EmployeeDetails;
import com.janta.billing.exception.SystemException;


public interface EmployeeDetailsService {

	EmployeeDetails getEmployeeDetailsById(Long employeeId);

	List<EmployeeDto> fetchEmployeesWithPendingApprovals();

	void updateApprovalStatus(Long id) throws SystemException;

}
