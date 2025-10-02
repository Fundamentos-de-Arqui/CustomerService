package com.soulware.platform.customerservice.cs.infrastructure.persistence.jpa.repositories;

import com.soulware.platform.customerservice.cs.domain.model.aggregates.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
