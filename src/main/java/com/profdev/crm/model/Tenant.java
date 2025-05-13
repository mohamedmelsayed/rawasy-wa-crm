package com.profdev.crm.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tenants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String domain;
    @NotBlank
    private String contactEmail;

    @JsonIgnore
    private String whatsappApiKey;
    @JsonIgnore
    private String whatsappPhoneNumberId;
    @JsonIgnore
    private String whatsappBusinessAccountId;
    @JsonIgnore
    private String whatsappApiUrl;
    @JsonIgnore
    private String whatsappWebhookUrl;

    @OneToMany(mappedBy = "tenant")
    @JsonIgnore
    private List<User> users;

    @OneToMany(mappedBy = "tenant")
    @JsonIgnore
    private List<Customer> customers;

    @OneToMany(mappedBy = "tenant")
    @JsonIgnore
    private List<Campaign> campaigns;

    @OneToMany(mappedBy = "tenant")
    @JsonIgnore
    private List<Subscription> subscriptions;

    @OneToMany(mappedBy = "tenant")
    @JsonIgnore
    private List<MessageTemplate> messageTemplates;

    @OneToMany(mappedBy = "tenant")
    @JsonIgnore
    private List<MarketingGroup> marketingGroups;

    @OneToMany(mappedBy = "tenant")
    @JsonIgnore
    private List<LookupTable> lookupTables;

    @OneToMany(mappedBy = "tenant")
    @JsonIgnore
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "tenant")
    @JsonIgnore
    private List<Quotation> quotations;

    @OneToMany(mappedBy = "tenant")
    @JsonIgnore
    private List<Feedback> feedbacks;
}
