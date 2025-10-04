package com.soulware.platform.customerservice.cs.domain.services;

import com.soulware.platform.customerservice.cs.domain.model.aggregates.TherapistProfile;
import com.soulware.platform.customerservice.cs.domain.model.queries.GetAllTherapistsQuery;
import com.soulware.platform.customerservice.cs.domain.model.queries.GetTherapistsByPatientQuery;

import java.util.List;

public interface TherapistQueryService {
    List<TherapistProfile> handle(GetAllTherapistsQuery query);
    List<TherapistProfile> handle(GetTherapistsByPatientQuery query);
}



