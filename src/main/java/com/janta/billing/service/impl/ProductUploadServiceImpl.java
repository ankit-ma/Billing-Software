package com.janta.billing.service.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	@Value("${solr.core}")
	private String core;

	@Autowired
	private ProductUploadRecordRepository productUploadRecordRepository;

	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private SolrClient solrClient;

	@Override
	public String processUploadedProductExcel(MultipartFile file) throws SystemException {
		ProductUploadRecord productUploadRecord = ProductUploadRecord.builder().status("In Progress")
				.fileName(file.getOriginalFilename()).build();
		try {

			productUploadRecordRepository.save(productUploadRecord);

			MyProductExcelProcessor excelProcessor = new MyProductExcelProcessor(file.getInputStream(),
					getProductHeader());
			excelProcessor.readExcel();
			inventoryRepository.saveAll(excelProcessor.getInventoryList());
			// solr hook
			List<SolrInputDocument> products = new LinkedList<>();
			excelProcessor.getInventoryList().forEach(inventory -> {
				SolrInputDocument document = new SolrInputDocument();
				document.addField("productName",
						inventory.getProductName() + " - " +  inventory.getBrandName());
				document.addField("brandName", inventory.getBrandName());
				document.addField("mrp",  inventory.getMrp());
				document.addField("cgst", inventory.getCgst());
				document.addField("sgst",  inventory.getSgst());
				document.addField("discount",  inventory.getDiscount());
				document.addField("quantity",  inventory.getQuantity());
				document.addField("thresholdQuantity",  inventory.getThresholdQuantity());
				document.addField("additionalInfo",  inventory.getAdditionalInfo());
				document.addField("productId", inventory.getId());
				document.addField("category",inventory.getCategory().getCategoryName());
				products.add(document);
			});

			solrClient.deleteByQuery(core, "*:*");
			solrClient.add(core, products);
			solrClient.commit(core);

			productUploadRecord.setStatus("Completed");
			productUploadRecordRepository.save(productUploadRecord);
			return productUploadRecord.getId() + ".xlsx";
		} catch (Exception e) {
			productUploadRecord.setStatus("Error");
			productUploadRecordRepository.save(productUploadRecord);
			throw new SystemException(e.getMessage());
		}

	}

	private Map<Integer, String> getProductHeader() {
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
		headerMap.put(9, "Category");
		headerMap.put(10, "Image url");

		return headerMap;
	}

	@Override
	public Page<ProductUploadRecord> fetchUploadhistory(Integer size, Integer pageNumber) {

		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by("loggedDate").descending());
		return productUploadRecordRepository.findAll(pageable);
	}

}
