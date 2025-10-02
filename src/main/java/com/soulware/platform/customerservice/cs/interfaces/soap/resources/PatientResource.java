package com.soulware.platform.customerservice.cs.interfaces.soap.resources;

import com.soulware.platform.customerservice.cs.domain.model.valueobjects.DocumentType;


import java.time.LocalDate;

/** Output DTO for returning Patient data. */
public record PatientResource(
    Long id,
    String firstNames,
    String paternalSurname,
    String maternalSurname,
    DocumentType documentType,
    String documentNumber,
    String phone,
    LocalDate birthDate,
    String receiptType
) {}
