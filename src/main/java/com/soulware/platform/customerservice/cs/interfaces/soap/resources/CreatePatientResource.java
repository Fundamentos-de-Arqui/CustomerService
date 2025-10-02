package com.soulware.platform.customerservice.cs.interfaces.soap.resources;

import com.soulware.platform.customerservice.cs.domain.model.valueobjects.DocumentType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/** Input DTO for creating a Patient (your former “CreatePatientRequest”). */
public record CreatePatientResource(
    @NotBlank String firstNames,
    @NotBlank String paternalSurname,
    String maternalSurname,
    @NotNull DocumentType documentType,
    @NotBlank String documentNumber,
    @NotBlank String phone,
    @NotNull LocalDate birthDate,
    @NotNull String receiptType
) {}
