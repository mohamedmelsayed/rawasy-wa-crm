package com.profdev.crm.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "chat_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status; // e.g., ACTIVE, CLOSED
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @OneToMany(mappedBy = "chatSession")
    private List<Chat> chats;

    // Optionally, add typing/active agent tracking fields here
}
