package com.profdev.crm.dto;

import com.profdev.crm.model.Role;
import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String email;
    private String password;
    private Role role;
    private Long departmentId;
    private Long supervisorId;
    private Long tenantId;
}
