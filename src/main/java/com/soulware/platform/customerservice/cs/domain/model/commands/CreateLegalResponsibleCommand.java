package com.soulware.platform.customerservice.cs.domain.model.commands;

import java.time.LocalDate;

public record CreateLegalResponsibleCommand(
    String firstNames,
    String paternalSurname,
    String maternalSurname,
    String documentType,
    String documentNumber,
    String phone,
    String email,
    LocalDate birthDate,
    String relationship,
    String occupation,
    String address,
    Long patientProfileId
) {}





