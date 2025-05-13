package com.profdev.crm.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lookup_tables")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LookupTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String key;
    private String value;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
}
