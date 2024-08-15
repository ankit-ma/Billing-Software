package com.janta.billing.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.janta.billing.entity.ProductUploadRecord;
import com.janta.billing.exception.SystemException;
import com.janta.billing.repository.InventoryRepository;
import com.janta.billing.repository.ProductUploadRecordRepository;
import com.janta.billing.service.ProductUploadService;
import com.janta.billing.util.MyProductExcelProcessor;

@Service
public class ProductUploadServiceImpl implements ProductUploadService {

	@Autowired
	private ProductUploadRecordRepository productUploadRecordRepository;

	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Override
	public String processUploadedProductExcel(MultipartFile file) throws SystemException {
		ProductUploadRecord productUploadRecord = ProductUploadRecord.builder()
				.status("In Progress")
				.fileName(file.getOriginalFilename())
				.build();
		try {
			
			productUploadRecordRepository.save(productUploadRecord);
			
		MyProductExcelProcessor excelProcessor = new MyProductExcelProcessor(file.getInputStream(),getProductHeader());
		excelProcessor.readExcel();
		inventoryRepository.saveAll(excelProcessor.getInventoryList());
		productUploadRecord.setStatus("Completed");
		productUploadRecordRepository.save(productUploadRecord);
		return productUploadRecord.getId()+".xlsx";
		}
		catch (Exception e) {
			productUploadRecord.setStatus("Error");
			productUploadRecordRepository.save(productUploadRecord);
			throw new SystemException(e.getMessage());
		}

	}

	private Map<Integer, String> getProductHeader(){
		Map<Integer, String> headerMap = new LinkedHashMap<>();
		
		headerMap.put(0, "Product Name");
		headerMap.put(1, "Brand Name");
		headerMap.put(2, "MRP");
		headerMap.put(3, "CGST (%)");
		headerMap.put(4, "SGST (%)");
		headerMap.put(5, "Discount (%)");
		headerMap.put(6, "Quantity");
		headerMap.put(7, "Threshold quantity");
		headerMap.put(8, "Additional Information");
		return headerMap;
	}

	@Override
	public Page<ProductUploadRecord> fetchUploadhistory(Integer size, Integer pageNumber) {
	
		Pageable pageable = PageRequest.of(pageNumber, size);
		return productUploadRecordRepository.findAll(pageable);
	}

}
