package com.janta.billing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

	private String productId;
	private String productName;
	private String brandName;
	private String additionalInfo;
	private String category;
	private Double mrp;
	private Double cgst;
	private Double sgst;
	private Double discount;
	private Integer quantity;
	private Double total;
}
