package com.janta.billing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillCustomerDetailsDto {

	private String customerName;
	private String phoneNumber;
	private String address;
	private Double due;
	private Double currentBillAmount;
	private Double totalAmount;
}
