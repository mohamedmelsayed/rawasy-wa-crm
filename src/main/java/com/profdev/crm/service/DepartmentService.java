package com.profdev.crm.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.profdev.crm.model.Department;
import com.profdev.crm.repository.DepartmentRepository;
@Service
public class DepartmentService {
   @Autowired
    private DepartmentRepository departmentRepository;
     public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

}
