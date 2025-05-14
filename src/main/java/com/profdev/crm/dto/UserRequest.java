package com.profdev.crm.dto;

import com.profdev.crm.model.Department;
import com.profdev.crm.model.Role;
import com.profdev.crm.model.Tenant;
import com.profdev.crm.model.User;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {
  private String username;
    private String email;
    private String role;
    private Department department;
    private User supervisor; // Add this line
    private Tenant tenant;
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}
