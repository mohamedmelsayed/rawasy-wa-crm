package com.profdev.crm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TenantRequest {
    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    @NotBlank
    @Size(min = 3, max = 100)
    private String domain;

    @NotBlank
    @Email
    private String contactEmail;

    @NotBlank
    private String whatsappApiKey;

    private String whatsappPhoneNumberId;
    private String whatsappBusinessAccountId;
    private String whatsappApiUrl;
    private String whatsappWebhookUrl;

    @NotBlank
    private String adminPassword;
}
