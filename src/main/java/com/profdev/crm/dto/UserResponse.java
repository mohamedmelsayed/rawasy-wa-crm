package com.profdev.crm.dto;

import com.profdev.crm.model.Role;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private String departmentName;
    private String supervisorUsername;
    private Long tenantId;
}
