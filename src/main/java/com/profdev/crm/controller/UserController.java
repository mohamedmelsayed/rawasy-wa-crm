package com.profdev.crm.controller;

import com.profdev.crm.dto.UserRequest;
import com.profdev.crm.dto.UserResponse;
import com.profdev.crm.model.Role;
import com.profdev.crm.model.User;
import com.profdev.crm.service.UserService;
import com.profdev.crm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.stream().map(user -> {
            UserResponse response = new UserResponse();
            response.setId(user.getId());
            response.setUsername(user.getUsername());
            response.setEmail(user.getEmail());
            response.setRole(user.getRole());
            response.setDepartmentName(user.getDepartment().getName());
            response.setSupervisorUsername(user.getSupervisor().getUsername());
            // Add other fields as needed
            return response;
        }).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> {
                    UserResponse response = new UserResponse();
                    response.setId(user.getId());
                    response.setUsername(user.getUsername());
                    response.setEmail(user.getEmail());
                    response.setRole(user.getRole());
                    response.setDepartmentName(user.getDepartment().getName());
                    response.setSupervisorUsername(user.getSupervisor().getUsername());
                  
                    // Add other fields as needed
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest request) {
        // Map UserRequest to User entity
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        // Convert String to Role enum
        user.setRole(request.getRole() != null ? Role.valueOf(request.getRole()) : null);
        user.setDepartment(request.getDepartment());
        user.setTenant(request.getTenant());
        user.setSupervisor(request.getSupervisor());
        user.setPassword(request.getPassword());
        // Add other fields as needed

        User createdUser = userService.createUser(user);

        // Map User entity to UserResponse (assuming a constructor or builder exists)
        UserResponse response = new UserResponse();
        response.setId(createdUser.getId());
        response.setTenantId(createdUser.getTenant() != null ? createdUser.getTenant().getId() : null);
        response.setUsername(createdUser.getUsername());
        response.setEmail(createdUser.getEmail());
        response.setRole(createdUser.getRole());
        if (createdUser.getDepartment() != null) {
            response.setDepartmentName(createdUser.getDepartment().getName());
        } else {
            response.setDepartmentName(null);
        }
        if (createdUser.getSupervisor() != null) {
            response.setSupervisorUsername(createdUser.getSupervisor().getUsername());
        } else {
            response.setSupervisorUsername(null);
        }
        // Add other fields as needed

        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        // Map UserRequest to User entity
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole() != null ? Role.valueOf(request.getRole()) : null);
        // Validate and set Department
        if (request.getDepartment() != null && request.getDepartment().getId() != null) {
            departmentService.getDepartmentById(request.getDepartment().getId())
            .ifPresent(user::setDepartment);
        } else {
            user.setDepartment(null);
        }

        // Validate and set Supervisor
        if (request.getSupervisor() != null && request.getSupervisor().getId() != null) {
            userService.getUserById(request.getSupervisor().getId())
            .ifPresent(user::setSupervisor);
        } else {
            user.setSupervisor(null);
        }
        // Add other fields as needed

        return userService.updateUser(id, user)
                .map(updatedUser -> {
                    UserResponse response = new UserResponse();
                    response.setId(updatedUser.getId());
                    response.setUsername(updatedUser.getUsername());
                    response.setEmail(updatedUser.getEmail());
                    response.setRole(updatedUser.getRole());
                    if (updatedUser.getDepartment() != null) {
                        response.setDepartmentName(updatedUser.getDepartment().getName());
                    } else {
                        response.setDepartmentName(null);
                    }
                    if (updatedUser.getSupervisor() != null) {
                        response.setSupervisorUsername(updatedUser.getSupervisor().getUsername());
                    } else {
                        response.setSupervisorUsername(null);
                    }
                    // Add other fields as needed
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
