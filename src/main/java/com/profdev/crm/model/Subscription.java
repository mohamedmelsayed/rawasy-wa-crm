package com.profdev.crm.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "subscriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String planName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
}
