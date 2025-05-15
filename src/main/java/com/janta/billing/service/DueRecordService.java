package com.janta.billing.service;

import com.janta.billing.dto.DueRecordWithCustomerDTO;
import com.janta.billing.dto.MailServiceDTO;
import com.janta.billing.entity.DueRecord;

import java.util.List;
import java.util.Map;

public interface DueRecordService {

    public String sendDueNotification(MailServiceDTO mailServiceDTO);
    public List<DueRecordWithCustomerDTO> getDueLogs();
}
