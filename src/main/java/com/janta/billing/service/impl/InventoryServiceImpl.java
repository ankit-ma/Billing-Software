package com.janta.billing.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janta.billing.entity.Inventory;
import com.janta.billing.repository.InventoryRepository;
import com.janta.billing.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Override
	public void saveBulkInventoryList(List<Inventory> inventoryList) {
		// TODO Auto-generated method stub
		inventoryRepository.saveAll(inventoryList);
	}

}
