package com.profdev.crm.service;

import com.profdev.crm.dto.TenantRequest;
import com.profdev.crm.dto.TenantResponse;
import com.profdev.crm.model.Tenant;
import com.profdev.crm.model.User;
import com.profdev.crm.model.Role;
import com.profdev.crm.repository.TenantRepository;
import com.profdev.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TenantService {
    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<TenantResponse> getAllTenants() {
        return tenantRepository.findAll().stream().map(this::toTenantResponse).collect(Collectors.toList());
    }

    public Optional<TenantResponse> getTenantById(Long id) {
        return tenantRepository.findById(id).map(this::toTenantResponse);
    }

    public TenantResponse createTenant(TenantRequest request) {
        Tenant tenant = toTenant(request);
        tenant = tenantRepository.save(tenant);
        // Create admin user
        User admin = User.builder()
                .username(tenant.getContactEmail())
                .email(tenant.getContactEmail())
                .password(passwordEncoder.encode(request.getAdminPassword())) // Hash password
                .role(Role.ADMIN)
                .tenant(tenant)
                .build();
        userRepository.save(admin);
        // Create bot user
        String botEmail = "bot@" + tenant.getName().replaceAll("\\s+", "").toLowerCase() + ".bot";
        User bot = User.builder()
                .username("bot-" + tenant.getName().replaceAll("\\s+", "").toLowerCase())
                .email(botEmail)
                .password(passwordEncoder.encode("bot123")) // Hash bot password
                .role(Role.BOT)
                .tenant(tenant)
                .build();
        userRepository.save(bot);
        return toTenantResponse(tenant);
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
