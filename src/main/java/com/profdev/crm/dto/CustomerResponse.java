package com.profdev.crm.dto;

import lombok.Data;

@Data
public class CustomerResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Boolean marketingConsent;
    private Long tenantId;
    private String tenantName;
}
