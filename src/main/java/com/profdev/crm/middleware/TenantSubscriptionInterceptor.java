package com.profdev.crm.middleware;

import com.profdev.crm.model.Tenant;
import com.profdev.crm.model.Subscription;
import com.profdev.crm.repository.TenantRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
public class TenantSubscriptionInterceptor implements HandlerInterceptor {
    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tenantIdStr = request.getHeader("X-Tenant-Id");
        if (tenantIdStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing X-Tenant-Id header");
            return false;
        }
        Long tenantId;
        try {
            tenantId = Long.parseLong(tenantIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid X-Tenant-Id header");
            return false;
        }
        Optional<Tenant> tenantOpt = tenantRepository.findById(tenantId);
        if (tenantOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Tenant not found");
            return false;
        }
        Tenant tenant = tenantOpt.get();
        boolean hasActive = tenant.getSubscriptions() != null && tenant.getSubscriptions().stream().anyMatch(Subscription::getActive);
        if (!hasActive) {
            response.sendError(HttpServletResponse.SC_PAYMENT_REQUIRED, "Tenant subscription is not active");
            return false;
        }
        return true;
    }
}
