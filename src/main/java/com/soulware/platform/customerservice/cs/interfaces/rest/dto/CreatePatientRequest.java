package com.soulware.platform.customerservice.cs.interfaces.rest.dto;

import com.soulware.platform.customerservice.cs.domain.model.valueobjects.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

/**
 * Request DTO for creating a new patient
 */
public record CreatePatientRequest(
    @NotBlank(message = "First names are required")
    String firstNames,
    
    @NotBlank(message = "Paternal surname is required")
    String paternalSurname,
    
    String maternalSurname,
    
    @NotNull(message = "Document type is required")
    DocumentType documentType,
    
    @NotBlank(message = "Document number is required")
    String documentNumber,
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
    String phone,
    
    @NotNull(message = "Birth date is required")
    LocalDate birthDate,
    
    @NotBlank(message = "Receipt type is required")
    String receiptType
) {}


