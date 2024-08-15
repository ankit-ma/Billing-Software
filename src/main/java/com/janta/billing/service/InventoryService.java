package com.janta.billing.service;

import java.util.List;

import com.janta.billing.entity.Inventory;

public interface InventoryService {

	void saveBulkInventoryList(List<Inventory> inventoryList);

}
