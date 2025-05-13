package com.profdev.crm.controller;

import com.profdev.crm.dto.TenantRequest;
import com.profdev.crm.dto.TenantResponse;
import com.profdev.crm.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {
    @Autowired
    private TenantService tenantService;

    @GetMapping
    public List<TenantResponse> getAllTenants() {
        return tenantService.getAllTenants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantResponse> getTenantById(@PathVariable Long id) {
        return tenantService.getTenantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TenantResponse createTenant(@RequestBody TenantRequest tenantRequest) {
        return tenantService.createTenant(tenantRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TenantResponse> updateTenant(@PathVariable Long id, @RequestBody TenantRequest tenantRequest) {
        return tenantService.updateTenant(id, tenantRequest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenant(@PathVariable Long id) {
        tenantService.deleteTenant(id);
        return ResponseEntity.noContent().build();
    }
}
