package com.soulware.platform.customerservice.cs.domain.model.commands;


import com.soulware.platform.customerservice.cs.domain.model.valueobjects.DocumentType;

import java.time.LocalDate;

public record CreatePatientCommand(
    // Basic patient info
    String firstNames,
    String paternalSurname,
    String maternalSurname,
    DocumentType documentType,
    String documentNumber,
    String phone,
    LocalDate birthDate,
    String receiptType,
    
    // Additional personal info
    String email,
    String birthPlace,
    Integer ageFirstAppointment,
    Integer ageCurrent,
    String gender,
    String maritalStatus,
    String religion,
    String educationLevel,
    String occupation,
    String currentEducationalInstitution,
    
    // Address info
    String currentAddress,
    String district,
    String province,
    String region,
    String country,
    
    // Medical info
    String medicalDiagnosis,
    String problemIdentified,
    String additionalNotes,
    
    // Billing info
    String businessName,
    String holder,
    String rucOrDni,
    String billingAddress
) {
}
