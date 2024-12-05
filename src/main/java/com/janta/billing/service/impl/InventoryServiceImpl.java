package com.janta.billing.service.impl;

import java.util.LinkedList;
import java.util.List;


import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.janta.billing.entity.Inventory;
import com.janta.billing.exception.SystemException;
import com.janta.billing.repository.InventoryRepository;
import com.janta.billing.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {
	
	private static Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);
	
	@Value("${solr.core}")
	private String core;

	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Autowired
	private SolrClient solrClient;
	
	@Override
	public void saveBulkInventoryList(List<Inventory> inventoryList) {
		try {
		inventoryRepository.saveAll(inventoryList);
		List<SolrInputDocument> products = new LinkedList<>();
		inventoryList.forEach(inventory ->{
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
			products.add(document);
		});
		
		solrClient.add(core, products);
		solrClient.commit(core);
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			throw new SystemException(e.getMessage());
		}
	}

	@Override
	public Page<Inventory> fetchInventryDetails(Integer size, Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by("loggedDate").descending());
		return inventoryRepository.findAll(pageable);

	}

}
