package com.janta.billing.service;

import com.janta.billing.dto.CustomerDetailsDto;
import com.janta.billing.entity.CustomerDetails;
import com.janta.billing.entity.EmployeeDetails;

public interface CustomerDetailsService {

	void saveCustomerDetails(CustomerDetailsDto customerDetailsDto, EmployeeDetails employeeDetails);

	CustomerDetails fetchCustomerDetails(String phoneNumber);

}
