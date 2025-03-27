package com.janta.billing.service.impl;

import com.janta.billing.repository.BillRepository;
import com.janta.billing.service.DashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    Logger logger = LoggerFactory.getLogger(DashboardServiceImpl.class);
    @Autowired
    BillRepository billRepository;

    @Override
    public Map<String, Object> getDashboard() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastWeek = now.minusWeeks(1).plusDays(1);
        LocalDateTime lastMonth = now.minusMonths(1).plusDays(1);
        List<Map<String,Object>> lastWeekSalesReport = billRepository.findSalesInGivenRange(lastWeek,now);
        List<Map<String,Object>> lastMonthSalesReport = billRepository.findSalesInGivenRange(lastMonth,now);

        List<Long> salesPerDayForWeek = new ArrayList<>();
        List<Long> salesPerMonthForWeek = new ArrayList<>();
        lastWeekSalesReport.forEach(data->{
            salesPerDayForWeek.add((Long)data.get("bill_count"));
        });
        lastMonthSalesReport.forEach(data->{
            salesPerMonthForWeek.add((Long)data.get("bill_count"));
        });
        logger.info("lastWeekSalesReport = " + salesPerDayForWeek);
        logger.info("lastMonthSalesReport = " + salesPerMonthForWeek);

        Map<String,Object> salesInfo = billRepository.findTotalSalesForTodayAndLastMonth();
        logger.info("salesInfo = " + salesInfo);
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("salesPerDayForWeek", salesPerDayForWeek);
        dashboard.put("salesPerMonthForWeek", salesPerMonthForWeek);
        dashboard.put("todaySales",salesInfo.get("today_sales"));
        dashboard.put("monthSales",salesInfo.get("last_month_sales"));
        return dashboard;
    }
}
