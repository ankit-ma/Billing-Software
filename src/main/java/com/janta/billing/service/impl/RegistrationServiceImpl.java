package com.janta.billing.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.janta.billing.dto.EmployeeDto;
import com.janta.billing.entity.EmployeeDetails;
import com.janta.billing.entity.RoleMaster;
import com.janta.billing.exception.SystemException;
import com.janta.billing.repository.EmployeeDeatilRepository;
import com.janta.billing.repository.RoleMasterRepository;
import com.janta.billing.service.RegistrationService;
import com.janta.billing.util.Constants;


@Service

public class RegistrationServiceImpl implements RegistrationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationServiceImpl.class);
	
	@Autowired
	private EmployeeDeatilRepository employeeDeatilRepository;
	@Autowired
	private RoleMasterRepository roleMasterRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Override
	public String registerAnEmployee(EmployeeDto employeeDto) {
		try {
			EmployeeDetails employee = new EmployeeDetails();
			BeanUtils.copyProperties(employee, employeeDto);
			employee.setPassword(passwordEncoder.encode(employee.getPassword()));
			LOGGER.error(employee.toString());
			RoleMaster roleMaster = roleMasterRepository.findByRoleName(employeeDto.getDesignation());
			employee.setRole(roleMaster);
			EmployeeDetails existingEmployee = employeeDeatilRepository.findByPhoneNumber(employee.getPhoneNumber());
			if(existingEmployee==null)
			{
				employeeDeatilRepository.save(employee);
			}
			else {
				employee.setId(existingEmployee.getId());
				employeeDeatilRepository.save(employee);
				return "User Already present - Please login";
			}
		}
		catch(Exception e) {
			throw new SystemException(e.getMessage());
		}
		
		return "Succesfully Registered - Will able to login after Admin approval";
	}
	@Override
	public EmployeeDetails fetchEmployeeDetails(String email) {
		
		EmployeeDetails employeeDetails = employeeDeatilRepository.findEmployeeDetailsByEmail(email);
		return employeeDetails;
	}

}
