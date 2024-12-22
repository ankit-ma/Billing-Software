package com.janta.billing.service.impl;

import java.util.*;

import com.janta.billing.dto.CustomerDetailsDashboardDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

	@Override
	public CustomerDetails fetchCustomerDetails(String phoneNumber) {
		Optional<CustomerDetails> cuOptional = customerDetailsRepository.findByPhoneNumber(phoneNumber);
		return cuOptional.isPresent()?cuOptional.get():null;
	}

	@Override
	public Map<String,Object> fetchCustomerDetailsDashboard(Integer limit, Integer page) {

		Integer offset = limit*page;
		List<Map<String, Object>> customerDetails = customerDetailsRepository.fetchCustomerDetails(limit, offset);

		// Fetch total count
		int totalRecords = customerDetailsRepository.fetchTotalCustomerCount();

		// Construct response
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("data", customerDetails);
		response.put("totalRecords", totalRecords);
		response.put("limit", limit);
		response.put("offset", offset);

		return response;
    }

}
