package com.profdev.crm.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(unique = true)
    private String username;

    @JsonIgnore
    @NotBlank
    @Size(min = 8)
    private String password;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @OneToMany(mappedBy = "createdBy")
    private java.util.List<Campaign> campaigns;

    @OneToMany(mappedBy = "createdBy")
    private java.util.List<Ticket> tickets;

    @OneToMany(mappedBy = "createdBy")
    private java.util.List<Quotation> quotations;
    // Add role field later as needed

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private User supervisor;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Enumerated(EnumType.STRING)
    private Role role;
}
