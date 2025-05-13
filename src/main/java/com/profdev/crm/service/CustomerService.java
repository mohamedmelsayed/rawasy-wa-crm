package com.profdev.crm.service;

import com.profdev.crm.dto.CustomerRequest;
import com.profdev.crm.dto.CustomerResponse;
import com.profdev.crm.model.Customer;
import com.profdev.crm.model.Tenant;
import com.profdev.crm.repository.CustomerRepository;
import com.profdev.crm.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TenantRepository tenantRepository;

    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public Optional<CustomerResponse> getCustomerById(Long id) {
        return customerRepository.findById(id).map(this::toResponse);
    }

    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = toCustomer(request);
        return toResponse(customerRepository.save(customer));
    }

    public Optional<CustomerResponse> updateCustomer(Long id, CustomerRequest request) {
        return customerRepository.findById(id).map(customer -> {
            customer.setName(request.getName());
            customer.setEmail(request.getEmail());
            customer.setPhone(request.getPhone());
            customer.setAddress(request.getAddress());
            customer.setMarketingConsent(request.getMarketingConsent());
            if (request.getTenantId() != null) {
                tenantRepository.findById(request.getTenantId()).ifPresent(customer::setTenant);
            }
            return toResponse(customerRepository.save(customer));
        });
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public List<CustomerResponse> importCustomersFromCsv(MultipartFile file) {
        List<CustomerResponse> imported = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String header = reader.readLine(); // skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 6) {
                    CustomerRequest req = new CustomerRequest();
                    req.setName(fields[0]);
                    req.setEmail(fields[1]);
                    req.setPhone(fields[2]);
                    req.setAddress(fields[3]);
                    req.setMarketingConsent(Boolean.parseBoolean(fields[4]));
                    req.setTenantId(Long.parseLong(fields[5]));
                    imported.add(createCustomer(req));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to import customers from CSV", e);
        }
        return imported;
    }

    private Customer toCustomer(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        customer.setMarketingConsent(request.getMarketingConsent());
        if (request.getTenantId() != null) {
            tenantRepository.findById(request.getTenantId()).ifPresent(customer::setTenant);
        }
        return customer;
    }

    private CustomerResponse toResponse(Customer customer) {
        CustomerResponse dto = new CustomerResponse();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());
        dto.setMarketingConsent(customer.getMarketingConsent());
        if (customer.getTenant() != null) {
            dto.setTenantId(customer.getTenant().getId());
            dto.setTenantName(customer.getTenant().getName());
        }
        return dto;
    }
}
