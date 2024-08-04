package com.janta.billing.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janta.billing.entity.EmployeeDetails;
import com.janta.billing.repository.EmployeeDeatilRepository;
import com.janta.billing.service.EmployeeDetailsService;

@Service
public class EmployeeServiceImpl implements EmployeeDetailsService {

	@Autowired
	private EmployeeDeatilRepository employeeDeatilRepository;
	
	@Override
	public EmployeeDetails getEmployeeDetailsById(Long employeeId) {
		Optional<EmployeeDetails>emOptional= employeeDeatilRepository.findById(employeeId);
		
		return emOptional.isPresent()?emOptional.get():null;
	}

}
