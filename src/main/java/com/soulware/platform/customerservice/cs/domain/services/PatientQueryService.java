package com.soulware.platform.customerservice.cs.domain.services;

import com.soulware.platform.customerservice.cs.domain.model.aggregates.Patient;
import com.soulware.platform.customerservice.cs.domain.model.queries.GetAllPatientsQuery;

import java.util.List;

public interface PatientQueryService {
    List<Patient> handle(GetAllPatientsQuery query);
}
