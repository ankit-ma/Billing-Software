package com.janta.billing.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class MailServiceDTO {
    private String to;
    private String subject;
    private String templateName;
    private Map<String, Object> model;
}
