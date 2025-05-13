package com.profdev.crm.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Boolean marketingConsent;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
}
