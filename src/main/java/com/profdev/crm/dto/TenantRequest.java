package com.profdev.crm.dto;

import lombok.Data;

@Data
public class TenantRequest {
    private String name;
    private String domain;
    private String contactEmail;
    private String whatsappApiKey;
    private String whatsappPhoneNumberId;
    private String whatsappBusinessAccountId;
    private String whatsappApiUrl;
    private String whatsappWebhookUrl;
}
