package com.janta.billing.util;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.janta.billing.entity.Inventory;
import com.janta.billing.service.InventoryService;
import com.janta.billing.service.impl.InventoryServiceImpl;

import lombok.Data;



@Data
public class MyProductExcelProcessor extends ExcelReaderProcessing{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private List<Inventory> inventoryList = new LinkedList<>();
	private static int BATCH_SIZE = 50;
	
	
	public MyProductExcelProcessor(InputStream file,Map<Integer, String> headerMap) {
		
		super(file,headerMap);
	}

	@Override
	public void beforeProcessing() {
		logger.error("Before Processing");
		
	}

	@Override
	public void processRow(Map<Integer, String> rowData) {
		logger.error("Row:: {}",rowData);

			Inventory inventory = Inventory.builder()
					.productName(rowData.get(0))
					.brandName(rowData.get(1))
					.mrp(Double.parseDouble(rowData.get(2)))
					.cgst(Double.parseDouble(rowData.get(3)))
					.sgst(Double.parseDouble(rowData.get(4)))
					.discount(Double.parseDouble(rowData.get(5)))
					.quantity(Integer.parseInt(rowData.get(6).split("\\.")[0]))
					.thresholdQuantity(Integer.parseInt(rowData.get(7).split("\\.")[0]))
					.additionalInfo(rowData.get(8))
					.loggedBy(1l)
					.lastUpdatedBy(1l)
					
					.build();
			inventoryList.add(inventory);
		
	}

	@Override
	public void afterProcessing() {
		logger.error("After Processing");

	}

}
