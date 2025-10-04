package com.soulware.platform.customerservice.cs.infrastructure.persistence.jpa.repositories;

import com.soulware.platform.customerservice.cs.domain.model.aggregates.LegalResponsibleProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LegalResponsibleProfileRepository extends JpaRepository<LegalResponsibleProfile, Long> {
    List<LegalResponsibleProfile> findByPatientProfileId(Long patientProfileId);
}
