package com.janta.billing.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.janta.billing.entity.CustomerDetails;

@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, Long> {

	Optional<CustomerDetails> findByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT " +
            "c.id AS Id, " +
            "c.customer_name AS customerName, " +
            "c.phone_number AS phoneNumber, " +
            "c.email AS email ," +
            "c.address AS address, " +
            "COALESCE(d.due_amount, 0) AS dueAmount, " +
            "MAX(b.logged_date) AS lastBilledDate, " +
            "COALESCE(COUNT(b.id), 0) AS bills " +
            "FROM customer_details c " +
            "LEFT JOIN due_record d ON d.customer_id = c.id " +
            "LEFT JOIN bill_record b ON b.customer_details_id = c.id " +
            "GROUP BY c.id,c.customer_name, c.phone_number, c.address, d.due_amount " +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Map<String,Object>> fetchCustomerDetails(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT COUNT(DISTINCT c.id) " +
            "FROM customer_details c " +
            "LEFT JOIN due_record d ON d.customer_id = c.id " +
            "LEFT JOIN bill_record b ON b.customer_details_id = c.id", nativeQuery = true)
    int fetchTotalCustomerCount();

}
