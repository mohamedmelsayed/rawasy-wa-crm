package com.profdev.crm.controller;

import com.profdev.crm.model.User;
import com.profdev.crm.repository.UserRepository;
import com.profdev.crm.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        User user = userRepository.findAll().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst().orElse(null);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        claims.put("userId", user.getId());
        claims.put("tenantId", user.getTenant() != null ? user.getTenant().getId() : null);
        String token = jwtUtil.generateToken(user.getUsername(), claims);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("role", user.getRole().name());
        response.put("userId", user.getId());
        response.put("tenantId", user.getTenant() != null ? user.getTenant().getId() : null);
        return ResponseEntity.ok(response);
    }
}
