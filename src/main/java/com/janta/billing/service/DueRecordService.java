package com.janta.billing.service;

import com.janta.billing.dto.MailServiceDTO;

public interface DueRecordService {

    public String sendDueNotification(MailServiceDTO mailServiceDTO);
}
