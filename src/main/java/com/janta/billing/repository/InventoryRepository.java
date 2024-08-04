package com.janta.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.janta.billing.entity.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {

}
