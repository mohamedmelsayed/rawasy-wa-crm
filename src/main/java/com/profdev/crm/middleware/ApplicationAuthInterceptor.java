package com.profdev.crm.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.annotation.PostConstruct;

import java.util.Arrays;
import java.util.Set;

@Component
public class ApplicationAuthInterceptor implements HandlerInterceptor {
    private Set<String> validAppKeys;

    @Value("${app.keys:demo-app-key-123,another-key-456}")
    private String appKeysProperty;

    @PostConstruct
    public void init() {
        validAppKeys = Arrays.stream(appKeysProperty.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(java.util.stream.Collectors.toSet());
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String appKey = request.getHeader("X-App-Key");
        if (appKey == null || !validAppKeys.contains(appKey)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or missing application key");
            return false;
        }
        return true;
    }
}
