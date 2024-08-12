package com.janta.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.janta.billing.entity.ProductUploadRecord;

@Repository
public interface ProductUploadRecordRepository extends JpaRepository<ProductUploadRecord, String> {

}
