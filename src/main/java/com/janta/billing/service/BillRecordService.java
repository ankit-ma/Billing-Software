package com.janta.billing.service;

import com.janta.billing.dto.ApiResponse;
import com.janta.billing.dto.BillDto;
import com.janta.billing.entity.CustomerDetails;

public interface BillRecordService {

	public ApiResponse<String> generateBillForCustomer(CustomerDetails customerDetails,BillDto billDto);
}
