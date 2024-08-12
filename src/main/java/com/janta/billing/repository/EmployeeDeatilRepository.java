package com.janta.billing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.janta.billing.entity.EmployeeDetails;

@Repository
public interface EmployeeDeatilRepository extends JpaRepository<EmployeeDetails, Long> {

	EmployeeDetails findByPhoneNumber(String phoneNumber);
	
	@Query("SELECT emp FROM EmployeeDetails emp WHERE emp.email = :email and emp.isApproved=true")
	Optional<EmployeeDetails> findByEmail(@Param("email") String email);

	EmployeeDetails findEmployeeDetailsByEmail(String email);

	@Query("SELECT emp FROM EmployeeDetails emp WHERE emp.isApproved=false")
	List<EmployeeDetails> findAllWithIsApprovedFalse();

}
