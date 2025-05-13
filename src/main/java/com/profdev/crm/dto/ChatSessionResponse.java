package com.profdev.crm.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChatSessionResponse {
    private Long id;
    private String status;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private Long customerId;
    private String customerName;
    private Long assignedUserId;
    private String assignedUsername;
    private Long tenantId;
}
