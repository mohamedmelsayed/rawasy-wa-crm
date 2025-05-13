package com.profdev.crm.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "marketing_groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketingGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Group label/interest/reason

    @ManyToMany
    @JoinTable(
        name = "marketing_group_customers",
        joinColumns = @JoinColumn(name = "group_id"),
        inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private Set<Customer> customers;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
}
