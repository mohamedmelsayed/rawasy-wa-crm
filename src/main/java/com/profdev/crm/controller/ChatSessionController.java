package com.profdev.crm.controller;

import com.profdev.crm.dto.ChatSessionRequest;
import com.profdev.crm.dto.ChatSessionResponse;
import com.profdev.crm.service.ChatSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class ChatSessionController {
    @Autowired
    private ChatSessionService chatSessionService;

    @GetMapping
    public List<ChatSessionResponse> getAllSessions() {
        return chatSessionService.getAllSessions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatSessionResponse> getSessionById(@PathVariable Long id) {
        return chatSessionService.getSessionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ChatSessionResponse createSession(@RequestBody ChatSessionRequest request) {
        return chatSessionService.createSession(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChatSessionResponse> updateSession(@PathVariable Long id, @RequestBody ChatSessionRequest request) {
        return chatSessionService.updateSession(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        chatSessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}
