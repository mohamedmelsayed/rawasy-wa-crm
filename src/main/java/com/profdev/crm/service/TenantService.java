package com.profdev.crm.service;

import com.profdev.crm.dto.TenantRequest;
import com.profdev.crm.dto.TenantResponse;
import com.profdev.crm.model.Tenant;
import com.profdev.crm.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TenantService {
    @Autowired
    private TenantRepository tenantRepository;

    public List<TenantResponse> getAllTenants() {
        return tenantRepository.findAll().stream().map(this::toTenantResponse).collect(Collectors.toList());
    }

    public Optional<TenantResponse> getTenantById(Long id) {
        return tenantRepository.findById(id).map(this::toTenantResponse);
    }

    public TenantResponse createTenant(TenantRequest request) {
        Tenant tenant = toTenant(request);
        return toTenantResponse(tenantRepository.save(tenant));
    }

    public Optional<TenantResponse> updateTenant(Long id, TenantRequest request) {
        return tenantRepository.findById(id).map(tenant -> {
            tenant.setName(request.getName());
            tenant.setDomain(request.getDomain());
            tenant.setContactEmail(request.getContactEmail());
            tenant.setWhatsappApiKey(request.getWhatsappApiKey());
            tenant.setWhatsappPhoneNumberId(request.getWhatsappPhoneNumberId());
            tenant.setWhatsappBusinessAccountId(request.getWhatsappBusinessAccountId());
            tenant.setWhatsappApiUrl(request.getWhatsappApiUrl());
            tenant.setWhatsappWebhookUrl(request.getWhatsappWebhookUrl());
            return toTenantResponse(tenantRepository.save(tenant));
        });
    }

    public void deleteTenant(Long id) {
        tenantRepository.deleteById(id);
    }

    private Tenant toTenant(TenantRequest request) {
        return Tenant.builder()
                .name(request.getName())
                .domain(request.getDomain())
                .contactEmail(request.getContactEmail())
                .whatsappApiKey(request.getWhatsappApiKey())
                .whatsappPhoneNumberId(request.getWhatsappPhoneNumberId())
                .whatsappBusinessAccountId(request.getWhatsappBusinessAccountId())
                .whatsappApiUrl(request.getWhatsappApiUrl())
                .whatsappWebhookUrl(request.getWhatsappWebhookUrl())
                .build();
    }

    private TenantResponse toTenantResponse(Tenant tenant) {
        TenantResponse dto = new TenantResponse();
        dto.setId(tenant.getId());
        dto.setName(tenant.getName());
        dto.setDomain(tenant.getDomain());
        dto.setContactEmail(tenant.getContactEmail());
        dto.setWhatsappPhoneNumberId(tenant.getWhatsappPhoneNumberId());
        dto.setWhatsappBusinessAccountId(tenant.getWhatsappBusinessAccountId());
        dto.setWhatsappApiUrl(tenant.getWhatsappApiUrl());
        dto.setWhatsappWebhookUrl(tenant.getWhatsappWebhookUrl());
        return dto;
    }
}
