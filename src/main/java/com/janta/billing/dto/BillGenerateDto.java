package com.janta.billing.dto;

import java.util.List;
import java.util.Map;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillGenerateDto {

	private BillCustomerDetailsDto customerDetails;
	private List<ProductDto> products;
	private Double totalPrice;
	private String paymentMode;
}
