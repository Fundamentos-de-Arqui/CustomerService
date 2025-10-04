package com.soulware.platform.customerservice.cs.application.internal.commandservices;

import com.soulware.platform.customerservice.cs.domain.model.aggregates.LegalResponsibleProfile;
import com.soulware.platform.customerservice.cs.domain.model.aggregates.PatientProfile;
import com.soulware.platform.customerservice.cs.domain.model.commands.CreateLegalResponsibleCommand;
import com.soulware.platform.customerservice.cs.domain.services.LegalResponsibleCommandService;
import com.soulware.platform.customerservice.cs.infrastructure.persistence.jpa.repositories.LegalResponsibleProfileRepository;
import com.soulware.platform.customerservice.cs.infrastructure.persistence.jpa.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class LegalResponsibleCommandServiceImpl implements LegalResponsibleCommandService {
    private final LegalResponsibleProfileRepository repository;
    private final PatientRepository patientRepository;

    public LegalResponsibleCommandServiceImpl(LegalResponsibleProfileRepository repository, PatientRepository patientRepository) {
        this.repository = repository;
        this.patientRepository = patientRepository;
    }

    @Override
    public Optional<LegalResponsibleProfile> handle(CreateLegalResponsibleCommand command) {
        // Find the patient profile
        Optional<PatientProfile> patientOpt = patientRepository.findById(command.patientProfileId());
        if (patientOpt.isEmpty()) {
            return Optional.empty();
        }

        PatientProfile patient = patientOpt.get();
        
        // Create legal responsible profile
        LegalResponsibleProfile legalResponsible = new LegalResponsibleProfile(
            command.firstNames(),
            command.paternalSurname(),
            command.maternalSurname(),
            command.documentType(),
            command.documentNumber(),
            command.phone(),
            command.email(),
            command.birthDate(),
            command.relationship(),
            command.occupation(),
            command.address(),
            patient
        );

        LegalResponsibleProfile saved = repository.save(legalResponsible);
        return Optional.of(saved);
    }
}



