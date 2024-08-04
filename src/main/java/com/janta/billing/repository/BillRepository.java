package com.janta.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.janta.billing.entity.BillRecord;

@Repository
public interface BillRepository extends JpaRepository<BillRecord, String> {

}
