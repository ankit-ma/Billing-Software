package com.janta.billing.service.impl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janta.billing.dto.CustomerDetailsDto;
import com.janta.billing.entity.CustomerDetails;
import com.janta.billing.entity.EmployeeDetails;
import com.janta.billing.exception.SystemException;
import com.janta.billing.repository.CustomerDetailsRepository;
import com.janta.billing.service.CustomerDetailsService;



@Service
public class CustomerDetailsServiceImpl implements CustomerDetailsService {

	@Autowired
	private CustomerDetailsRepository customerDetailsRepository;
	
	@Override
	public void saveCustomerDetails(CustomerDetailsDto customerDetailsDto, EmployeeDetails employeeDetails) {
		try {
		Optional<CustomerDetails> existingCustomer = customerDetailsRepository.findByPhoneNumber(customerDetailsDto.getPhoneNumber());
		if(existingCustomer.isPresent()) {
			throw new SystemException("Customer already exist");
		}
		else {
			CustomerDetails customerDetails = CustomerDetails.builder()
					.loggedBy(employeeDetails.getId())
					.lastUpdatedBy(employeeDetails.getId())
					.build();
			BeanUtils.copyProperties(customerDetailsDto, customerDetails);
			
			customerDetailsRepository.save(customerDetails);
		}
		}
		catch(Exception e) {
			throw new SystemException(e.getMessage());
		}
		
	}

}
