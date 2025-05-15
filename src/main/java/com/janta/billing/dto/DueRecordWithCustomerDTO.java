package com.janta.billing.dto;

import com.janta.billing.entity.CustomerDetails;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DueRecordWithCustomerDTO {
    private Long id;
    private String customerName;
    private Double dueAmount;
    private Double lastDueAmount;
    private LocalDateTime loggedDate;
    private LocalDateTime lastUpdatedOn;
    private Long loggedBy;
    private Long lastUpdatedBy;
    private Integer rowstate;

}
