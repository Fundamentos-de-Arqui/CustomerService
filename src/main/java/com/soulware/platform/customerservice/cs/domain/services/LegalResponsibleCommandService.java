package com.soulware.platform.customerservice.cs.domain.services;

import com.soulware.platform.customerservice.cs.domain.model.aggregates.LegalResponsibleProfile;
import com.soulware.platform.customerservice.cs.domain.model.commands.CreateLegalResponsibleCommand;

import java.util.Optional;

public interface LegalResponsibleCommandService {
    Optional<LegalResponsibleProfile> handle(CreateLegalResponsibleCommand command);
}





