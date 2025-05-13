package com.profdev.crm.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "message_templates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String content;
    private String type; // e.g., WhatsApp, Email

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
}
