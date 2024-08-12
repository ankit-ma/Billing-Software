package com.janta.billing.util;

import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.janta.billing.entity.Inventory;

public class MyProductExcelProcessor extends ExcelReaderProcessing{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public MyProductExcelProcessor(InputStream file,Map<Integer, String> headerMap) {
		
		super(file,headerMap);
	}

	@Override
	public void beforeProcessing() {
		logger.error("Before Processing");
		
	}

	@Override
	public void processRow(Map<Integer, String> rowData) {
		logger.error("Header row:: {}",super.getHeadMap());
		Inventory inventory = Inventory.builder()
				.productName(rowData.get(0))
				.brandName(rowData.get(1))
				.mrp(Double.parseDouble(rowData.get(2)))
				.cgst(Double.parseDouble(rowData.get(3)))
				.sgst(Double.parseDouble(rowData.get(4)))
				.discount(Double.parseDouble(rowData.get(5)))
				.quantity(Integer.parseInt(rowData.get(6)))
				.thresholdQuantity(Integer.parseInt(rowData.get(7)))
				.additionalInfo(rowData.get(8))
				.build();
	}

	@Override
	public void afterProcessing() {
		logger.error("After Processing");
	}

}
