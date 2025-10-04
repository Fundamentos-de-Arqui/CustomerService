package com.soulware.platform.customerservice.cs.interfaces.rest.dto;

import com.soulware.platform.customerservice.cs.domain.model.valueobjects.DocumentType;

import java.time.LocalDate;

/**
 * Response DTO for patient data
 */
public record PatientResponse(
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

