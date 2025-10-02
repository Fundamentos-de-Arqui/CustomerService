package com.soulware.platform.customerservice.cs.application.internal.queryservices;

import com.soulware.platform.customerservice.cs.domain.model.aggregates.Patient;
import com.soulware.platform.customerservice.cs.domain.model.queries.GetAllPatientsQuery;
import com.soulware.platform.customerservice.cs.domain.services.PatientQueryService;
import com.soulware.platform.customerservice.cs.infrastructure.persistence.jpa.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PatientQueryServiceImpl implements PatientQueryService {
  private final PatientRepository repo;
  public PatientQueryServiceImpl(PatientRepository repo) { this.repo = repo; }
  @Override public List<Patient> handle(GetAllPatientsQuery q) { return repo.findAll(); }
}
