package com.soulware.platform.customerservice.cs.application.internal.queryservices;

import com.soulware.platform.customerservice.cs.domain.model.aggregates.TherapistProfile;
import com.soulware.platform.customerservice.cs.domain.model.queries.GetAllTherapistsQuery;
import com.soulware.platform.customerservice.cs.domain.model.queries.GetTherapistsByPatientQuery;
import com.soulware.platform.customerservice.cs.domain.services.TherapistQueryService;
import com.soulware.platform.customerservice.cs.infrastructure.persistence.jpa.repositories.TherapistProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TherapistQueryServiceImpl implements TherapistQueryService {
    private final TherapistProfileRepository repository;

    public TherapistQueryServiceImpl(TherapistProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TherapistProfile> handle(GetAllTherapistsQuery query) {
        return repository.findAll();
    }

    @Override
    public List<TherapistProfile> handle(GetTherapistsByPatientQuery query) {
        // For many-to-many relationship, we need to query through the patient entity
        // This is a simplified implementation - in a real scenario, you'd need a custom query
        return repository.findAll();
    }
}
