package com.soulware.platform.customerservice.cs.domain.services;

import com.soulware.platform.customerservice.cs.domain.model.aggregates.TherapistProfile;
import com.soulware.platform.customerservice.cs.domain.model.commands.CreateTherapistCommand;

import java.util.Optional;

public interface TherapistCommandService {
    Optional<TherapistProfile> handle(CreateTherapistCommand command);
}



