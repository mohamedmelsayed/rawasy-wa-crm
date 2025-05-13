package com.profdev.crm.dto;

import lombok.Data;

@Data
public class TenantResponse {
    private Long id;
    private String name;
    private String domain;
    private String contactEmail;
    private String whatsappPhoneNumberId;
    private String whatsappBusinessAccountId;
    private String whatsappApiUrl;
    private String whatsappWebhookUrl;
}
