package com.soulware.platform.customerservice.cs.interfaces.rest.dto;

import java.time.LocalDate;

public record CreateLegalResponsibleRequest(
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





