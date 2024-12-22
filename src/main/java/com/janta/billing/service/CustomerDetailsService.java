package com.janta.billing.service;

import com.janta.billing.dto.CustomerDetailsDashboardDTO;
import com.janta.billing.dto.CustomerDetailsDto;
import com.janta.billing.entity.CustomerDetails;
import com.janta.billing.entity.EmployeeDetails;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface CustomerDetailsService {

	void saveCustomerDetails(CustomerDetailsDto customerDetailsDto, EmployeeDetails employeeDetails);

	CustomerDetails fetchCustomerDetails(String phoneNumber);
	Map<String,Object> fetchCustomerDetailsDashboard(Integer size, Integer page);
}
