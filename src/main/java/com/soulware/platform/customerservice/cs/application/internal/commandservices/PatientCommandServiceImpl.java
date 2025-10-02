package com.soulware.platform.customerservice.cs.application.internal.commandservices;

import com.soulware.platform.customerservice.cs.domain.model.aggregates.Patient;
import com.soulware.platform.customerservice.cs.domain.model.commands.CreatePatientCommand;
import com.soulware.platform.customerservice.cs.domain.services.PatientCommandService;
import com.soulware.platform.customerservice.cs.infrastructure.persistence.jpa.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class PatientCommandServiceImpl implements PatientCommandService {
    private final PatientRepository patientRepository;

    public PatientCommandServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Optional<Patient> handle(CreatePatientCommand command) {
        return Optional.of(patientRepository.save(new Patient(command)));
    }
}
