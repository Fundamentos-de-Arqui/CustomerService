package com.soulware.platform.customerservice.cs.application.internal.commandservices;

import com.soulware.platform.customerservice.cs.domain.model.aggregates.TherapistProfile;
import com.soulware.platform.customerservice.cs.domain.model.commands.CreateTherapistCommand;
import com.soulware.platform.customerservice.cs.domain.services.TherapistCommandService;
import com.soulware.platform.customerservice.cs.infrastructure.persistence.jpa.repositories.TherapistProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class TherapistCommandServiceImpl implements TherapistCommandService {
    private final TherapistProfileRepository repository;

    public TherapistCommandServiceImpl(TherapistProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<TherapistProfile> handle(CreateTherapistCommand command) {
        TherapistProfile therapist = new TherapistProfile(
            command.firstNames(),
            command.paternalSurname(),
            command.maternalSurname(),
            command.documentType(),
            command.documentNumber(),
            command.phone(),
            command.email(),
            command.birthDate(),
            command.licenseNumber(),
            command.specialization(),
            command.university(),
            command.graduationDate(),
            command.certifications(),
            command.address(),
            command.district(),
            command.province(),
            command.region(),
            command.country()
        );

        TherapistProfile saved = repository.save(therapist);
        return Optional.of(saved);
    }
}





