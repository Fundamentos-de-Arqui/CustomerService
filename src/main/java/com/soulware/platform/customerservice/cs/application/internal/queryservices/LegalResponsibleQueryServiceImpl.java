package com.soulware.platform.customerservice.cs.application.internal.queryservices;

import com.soulware.platform.customerservice.cs.domain.model.aggregates.LegalResponsibleProfile;
import com.soulware.platform.customerservice.cs.domain.model.queries.GetAllLegalResponsiblesQuery;
import com.soulware.platform.customerservice.cs.domain.model.queries.GetLegalResponsiblesByPatientQuery;
import com.soulware.platform.customerservice.cs.domain.services.LegalResponsibleQueryService;
import com.soulware.platform.customerservice.cs.infrastructure.persistence.jpa.repositories.LegalResponsibleProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class LegalResponsibleQueryServiceImpl implements LegalResponsibleQueryService {
    private final LegalResponsibleProfileRepository repository;

    public LegalResponsibleQueryServiceImpl(LegalResponsibleProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<LegalResponsibleProfile> handle(GetAllLegalResponsiblesQuery query) {
        return repository.findAll();
    }

    @Override
    public List<LegalResponsibleProfile> handle(GetLegalResponsiblesByPatientQuery query) {
        return repository.findByPatientProfileId(query.patientProfileId());
    }
}





