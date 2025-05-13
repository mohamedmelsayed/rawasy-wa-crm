package com.profdev.crm.service;

import com.profdev.crm.dto.ChatSessionRequest;
import com.profdev.crm.dto.ChatSessionResponse;
import com.profdev.crm.model.ChatSession;
import com.profdev.crm.model.Customer;
import com.profdev.crm.model.Tenant;
import com.profdev.crm.model.User;
import com.profdev.crm.repository.ChatSessionRepository;
import com.profdev.crm.repository.CustomerRepository;
import com.profdev.crm.repository.TenantRepository;
import com.profdev.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatSessionService {
    @Autowired
    private ChatSessionRepository chatSessionRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TenantRepository tenantRepository;

    public List<ChatSessionResponse> getAllSessions() {
        return chatSessionRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public Optional<ChatSessionResponse> getSessionById(Long id) {
        return chatSessionRepository.findById(id).map(this::toResponse);
    }

    public ChatSessionResponse createSession(ChatSessionRequest request) {
        ChatSession session = new ChatSession();
        session.setStatus(request.getStatus());
        if (request.getCustomerId() != null) {
            customerRepository.findById(request.getCustomerId()).ifPresent(session::setCustomer);
        }
        if (request.getAssignedUserId() != null) {
            userRepository.findById(request.getAssignedUserId()).ifPresent(session::setAssignedUser);
        }
        if (request.getTenantId() != null) {
            tenantRepository.findById(request.getTenantId()).ifPresent(session::setTenant);
        }
        session.setStartedAt(java.time.LocalDateTime.now());
        return toResponse(chatSessionRepository.save(session));
    }

    public Optional<ChatSessionResponse> updateSession(Long id, ChatSessionRequest request) {
        return chatSessionRepository.findById(id).map(session -> {
            session.setStatus(request.getStatus());
            if (request.getCustomerId() != null) {
                customerRepository.findById(request.getCustomerId()).ifPresent(session::setCustomer);
            }
            if (request.getAssignedUserId() != null) {
                userRepository.findById(request.getAssignedUserId()).ifPresent(session::setAssignedUser);
            }
            if (request.getTenantId() != null) {
                tenantRepository.findById(request.getTenantId()).ifPresent(session::setTenant);
            }
            return toResponse(chatSessionRepository.save(session));
        });
    }

    public void deleteSession(Long id) {
        chatSessionRepository.deleteById(id);
    }

    private ChatSessionResponse toResponse(ChatSession session) {
        ChatSessionResponse dto = new ChatSessionResponse();
        dto.setId(session.getId());
        dto.setStatus(session.getStatus());
        dto.setStartedAt(session.getStartedAt());
        dto.setEndedAt(session.getEndedAt());
        if (session.getCustomer() != null) {
            dto.setCustomerId(session.getCustomer().getId());
            dto.setCustomerName(session.getCustomer().getName());
        }
        if (session.getAssignedUser() != null) {
            dto.setAssignedUserId(session.getAssignedUser().getId());
            dto.setAssignedUsername(session.getAssignedUser().getUsername());
        }
        if (session.getTenant() != null) {
            dto.setTenantId(session.getTenant().getId());
        }
        return dto;
    }
}
