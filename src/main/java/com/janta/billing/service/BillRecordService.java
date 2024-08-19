package com.janta.billing.service;

import org.springframework.http.ResponseEntity;

import com.janta.billing.dto.BillGenerateDto;
import com.janta.billing.dto.FileResponse;

import java.io.IOException;

import org.springframework.core.io.Resource;

public interface BillRecordService {

	public ResponseEntity<Resource> generateBillForCustomer(BillGenerateDto billGenerateDto,Long employeeId) throws IOException;


}
