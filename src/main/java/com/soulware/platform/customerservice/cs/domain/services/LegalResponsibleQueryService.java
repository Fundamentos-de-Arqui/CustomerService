package com.soulware.platform.customerservice.cs.domain.services;

import com.soulware.platform.customerservice.cs.domain.model.aggregates.LegalResponsibleProfile;
import com.soulware.platform.customerservice.cs.domain.model.queries.GetAllLegalResponsiblesQuery;
import com.soulware.platform.customerservice.cs.domain.model.queries.GetLegalResponsiblesByPatientQuery;

import java.util.List;

public interface LegalResponsibleQueryService {
    List<LegalResponsibleProfile> handle(GetAllLegalResponsiblesQuery query);
    List<LegalResponsibleProfile> handle(GetLegalResponsiblesByPatientQuery query);
}





