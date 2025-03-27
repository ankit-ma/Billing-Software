package com.janta.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.janta.billing.entity.BillRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface BillRepository extends JpaRepository<BillRecord, String> {

    @Query(
            value = "WITH RECURSIVE date_series AS (" +
                    "    SELECT :startDate AS logged_date " +
                    "    UNION ALL " +
                    "    SELECT DATE_ADD(logged_date, INTERVAL 1 DAY) " +
                    "    FROM date_series " +
                    "    WHERE logged_date < :now" +
                    ") " +
                    "SELECT ds.logged_date, COALESCE(COUNT(br.logged_date), 0) AS bill_count " +
                    "FROM date_series ds " +
                    "LEFT JOIN bill_record br ON DATE(br.logged_date) = ds.logged_date " +
                    "GROUP BY ds.logged_date " +
                    "ORDER BY ds.logged_date;",
            nativeQuery = true
    )
    List<Map<String, Object>> findSalesInGivenRange(@Param("startDate")LocalDateTime startDate, @Param("now") LocalDateTime now);
    @Query(
            value = "SELECT " +
                    "COALESCE(SUM(CASE WHEN DATE(logged_date) = CURRENT_DATE THEN bill_amount ELSE 0 END), 0) AS today_sales, " +
                    "COALESCE(SUM(CASE WHEN logged_date >= DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH) THEN bill_amount ELSE 0 END), 0) AS last_month_sales " +
                    "FROM bill_record",
            nativeQuery = true
    )
    Map<String, Object> findTotalSalesForTodayAndLastMonth();

}
