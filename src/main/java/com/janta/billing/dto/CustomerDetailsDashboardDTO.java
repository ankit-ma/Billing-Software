package com.janta.billing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
public class CustomerDetailsDashboardDTO {

    private String customerName;
    private String phoneNumber;
    private String address;
    private long dueAmount;
    private LocalDateTime lastBilledDate;
    private long totalNumberOfBills;

    public CustomerDetailsDashboardDTO(String customerName, String phoneNumber, String address, long dueAmount, LocalDateTime lastBilledDate, long totalNumberOfBills) {
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dueAmount = dueAmount;
        this.lastBilledDate = lastBilledDate;
        this.totalNumberOfBills = totalNumberOfBills;
    }
}
