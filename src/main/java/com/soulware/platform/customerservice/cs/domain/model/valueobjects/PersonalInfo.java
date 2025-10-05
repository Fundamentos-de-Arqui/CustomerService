package com.soulware.platform.customerservice.cs.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public record PersonalInfo(
    String firstNames,
    String paternalSurname,
    String maternalSurname,
    String identityDocumentNumber,
    DocumentType documentType,
    String phone,
    String email,
    String birthPlace,
    LocalDate birthDate,
    Integer ageFirstAppointment,
    Integer ageCurrent,
    String gender,
    String maritalStatus,
    String religion,
    String educationLevel,
    String occupation,
    String currentEducationalInstitution
) {
    public PersonalInfo {
        Objects.requireNonNull(firstNames, "firstNames cannot be null");
        Objects.requireNonNull(paternalSurname, "paternalSurname cannot be null");
        Objects.requireNonNull(identityDocumentNumber, "identityDocumentNumber cannot be null");
        Objects.requireNonNull(documentType, "documentType cannot be null");
        Objects.requireNonNull(birthDate, "birthDate cannot be null");
    }
}





