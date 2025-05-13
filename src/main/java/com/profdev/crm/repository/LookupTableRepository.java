package com.profdev.crm.repository;

import com.profdev.crm.model.LookupTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LookupTableRepository extends JpaRepository<LookupTable, Long> {
}
