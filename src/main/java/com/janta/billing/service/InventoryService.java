package com.janta.billing.service;

import java.util.List;

import com.janta.billing.entity.Inventory;
import org.springframework.data.domain.Page;

public interface InventoryService {

	void saveBulkInventoryList(List<Inventory> inventoryList);

    Page<Inventory> fetchInventryDetails(Integer size, Integer pageNumber);
}
