package com.janta.billing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.janta.billing.entity.EmployeeDetails;

@Repository
public interface EmployeeDeatilRepository extends JpaRepository<EmployeeDetails, Long> {

	EmployeeDetails findByPhoneNumber(String phoneNumber);

	Optional<EmployeeDetails> findByEmail(String username);

}
