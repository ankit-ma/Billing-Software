package com.janta.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.janta.billing.entity.DueRecord;

@Repository
public interface DueRecordRepository extends JpaRepository<DueRecord, Long> {

}
