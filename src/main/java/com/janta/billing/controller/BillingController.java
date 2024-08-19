package com.janta.billing.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.janta.billing.dto.BillGenerateDto;
import com.janta.billing.dto.FileResponse;
import com.janta.billing.service.BillRecordService;



@RestController
@RequestMapping("/bill")
public class BillingController {

	@Autowired
	private BillRecordService billRecordService;
	
	@PostMapping("/generate/{employeeId}")
	public ResponseEntity<Resource> generateBill(@PathVariable Long employeeId, @RequestBody BillGenerateDto billGenerateDto){
		
		try {
			return billRecordService.generateBillForCustomer(billGenerateDto,employeeId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
}
