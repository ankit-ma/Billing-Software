package com.janta.billing.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janta.billing.dto.EmployeeDto;
import com.janta.billing.entity.EmployeeDetails;
import com.janta.billing.exception.SystemException;
import com.janta.billing.repository.EmployeeDeatilRepository;
import com.janta.billing.service.EmployeeDetailsService;


@Service
public class EmployeeServiceImpl implements EmployeeDetailsService {

	private static Logger logger = LoggerFactory.getLogger(EmployeeDetailsService.class);
	
	@Autowired
	private EmployeeDeatilRepository employeeDeatilRepository;
	
	@Override
	public EmployeeDetails getEmployeeDetailsById(Long employeeId) {
		Optional<EmployeeDetails>emOptional= employeeDeatilRepository.findById(employeeId);
		
		return emOptional.isPresent()?emOptional.get():null;
	}

	@Override
	public List<EmployeeDto> fetchEmployeesWithPendingApprovals() {
		List<EmployeeDetails> employeeDetails = employeeDeatilRepository.findAllWithIsApprovedFalse();
		List<EmployeeDto> finalList = new ArrayList<>();
		for(EmployeeDetails emp:employeeDetails) {
			EmployeeDto employeeDto = new EmployeeDto();
			BeanUtils.copyProperties(emp, employeeDto);
			finalList.add(employeeDto);
		}
		return finalList;
	}

	@Override
	public void updateApprovalStatus(Long id)  {
		try {
		EmployeeDetails employeeDetails = employeeDeatilRepository.findById(id).orElseThrow(()-> new SystemException("Unable to update Approval: No user found"));
		
		employeeDetails.setIsApproved(true);
		logger.error("Approval before Update: ",employeeDetails);
		employeeDeatilRepository.save(employeeDetails);
		logger.error("Approval after Update: ",employeeDetails);
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		
	}

}
