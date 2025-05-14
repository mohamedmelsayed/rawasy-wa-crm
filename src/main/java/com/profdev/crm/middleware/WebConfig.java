package com.profdev.crm.middleware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private TenantSubscriptionInterceptor tenantSubscriptionInterceptor;

    @Autowired
    private ApplicationAuthInterceptor applicationAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(applicationAuthInterceptor)
                .addPathPatterns("/api/tenants/**"); // Only protect tenant routes for now
        registry.addInterceptor(tenantSubscriptionInterceptor)
                .addPathPatterns("/api/nopath/**"); // Apply to all API endpoints
    }
}

// this comment is for further code completion here we must get tenant id from the login user
// and check if the user has a subscription
// and if the subscription is active
// and if the subscription is not expired
// and if the subscription is not cancelled
// and if the subscription is not suspended

