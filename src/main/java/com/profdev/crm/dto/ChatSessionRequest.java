package com.profdev.crm.dto;

import lombok.Data;

@Data
public class ChatSessionRequest {
    private Long customerId;
    private Long assignedUserId;
    private Long tenantId;
    private String status;
}
