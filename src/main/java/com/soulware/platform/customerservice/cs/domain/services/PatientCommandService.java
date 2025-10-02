package com.soulware.platform.customerservice.cs.domain.services;

import com.soulware.platform.customerservice.cs.domain.model.aggregates.Patient;
import com.soulware.platform.customerservice.cs.domain.model.commands.CreatePatientCommand;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface PatientCommandService {
    Optional<Patient> handle(CreatePatientCommand command);
}
