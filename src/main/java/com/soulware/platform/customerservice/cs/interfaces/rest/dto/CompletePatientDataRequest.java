package com.soulware.platform.customerservice.cs.interfaces.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;

public record CompletePatientDataRequest(
    // Patient data
    @JsonProperty("firstNames") String firstNames,
    @JsonProperty("paternalSurname") String paternalSurname,
    @JsonProperty("maternalSurname") String maternalSurname,
    @JsonProperty("documentType") String documentType,
    @JsonProperty("documentNumber") String documentNumber,
    @JsonProperty("phone") String phone,
    @JsonProperty("email") String email,
    @JsonProperty("birthDate") LocalDate birthDate,
    @JsonProperty("birthPlace") String birthPlace,
    @JsonProperty("ageFirstAppointment") Integer ageFirstAppointment,
    @JsonProperty("ageCurrent") Integer ageCurrent,
    @JsonProperty("gender") String gender,
    @JsonProperty("maritalStatus") String maritalStatus,
    @JsonProperty("religion") String religion,
    @JsonProperty("educationLevel") String educationLevel,
    @JsonProperty("occupation") String occupation,
    @JsonProperty("currentEducationalInstitution") String currentEducationalInstitution,
    @JsonProperty("currentAddress") String currentAddress,
    @JsonProperty("district") String district,
    @JsonProperty("province") String province,
    @JsonProperty("region") String region,
    @JsonProperty("country") String country,
    @JsonProperty("medicalDiagnosis") String medicalDiagnosis,
    @JsonProperty("problemIdentified") String problemIdentified,
    @JsonProperty("additionalNotes") String additionalNotes,
    @JsonProperty("receiptType") String receiptType,
    @JsonProperty("businessName") String businessName,
    @JsonProperty("holder") String holder,
    @JsonProperty("rucOrDni") String rucOrDni,
    @JsonProperty("billingAddress") String billingAddress,
    
    // Legal responsibles data
    @JsonProperty("legalResponsibles") List<LegalResponsibleData> legalResponsibles,
    
    // Therapists data
    @JsonProperty("therapists") List<TherapistData> therapists
) {
    @com.fasterxml.jackson.annotation.JsonCreator
    public CompletePatientDataRequest(
        @JsonProperty("firstNames") String firstNames,
        @JsonProperty("paternalSurname") String paternalSurname,
        @JsonProperty("maternalSurname") String maternalSurname,
        @JsonProperty("documentType") String documentType,
        @JsonProperty("documentNumber") String documentNumber,
        @JsonProperty("phone") String phone,
        @JsonProperty("email") String email,
        @JsonProperty("birthDate") LocalDate birthDate,
        @JsonProperty("birthPlace") String birthPlace,
        @JsonProperty("ageFirstAppointment") Integer ageFirstAppointment,
        @JsonProperty("ageCurrent") Integer ageCurrent,
        @JsonProperty("gender") String gender,
        @JsonProperty("maritalStatus") String maritalStatus,
        @JsonProperty("religion") String religion,
        @JsonProperty("educationLevel") String educationLevel,
        @JsonProperty("occupation") String occupation,
        @JsonProperty("currentEducationalInstitution") String currentEducationalInstitution,
        @JsonProperty("currentAddress") String currentAddress,
        @JsonProperty("district") String district,
        @JsonProperty("province") String province,
        @JsonProperty("region") String region,
        @JsonProperty("country") String country,
        @JsonProperty("medicalDiagnosis") String medicalDiagnosis,
        @JsonProperty("problemIdentified") String problemIdentified,
        @JsonProperty("additionalNotes") String additionalNotes,
        @JsonProperty("receiptType") String receiptType,
        @JsonProperty("businessName") String businessName,
        @JsonProperty("holder") String holder,
        @JsonProperty("rucOrDni") String rucOrDni,
        @JsonProperty("billingAddress") String billingAddress,
        @JsonProperty("legalResponsibles") List<LegalResponsibleData> legalResponsibles,
        @JsonProperty("therapists") List<TherapistData> therapists
    ) {
        this.firstNames = firstNames;
        this.paternalSurname = paternalSurname;
        this.maternalSurname = maternalSurname;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.phone = phone;
        this.email = email;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        this.ageFirstAppointment = ageFirstAppointment;
        this.ageCurrent = ageCurrent;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.religion = religion;
        this.educationLevel = educationLevel;
        this.occupation = occupation;
        this.currentEducationalInstitution = currentEducationalInstitution;
        this.currentAddress = currentAddress;
        this.district = district;
        this.province = province;
        this.region = region;
        this.country = country;
        this.medicalDiagnosis = medicalDiagnosis;
        this.problemIdentified = problemIdentified;
        this.additionalNotes = additionalNotes;
        this.receiptType = receiptType;
        this.businessName = businessName;
        this.holder = holder;
        this.rucOrDni = rucOrDni;
        this.billingAddress = billingAddress;
        this.legalResponsibles = legalResponsibles;
        this.therapists = therapists;
    }
    public record LegalResponsibleData(
        @JsonProperty("firstNames") String firstNames,
        @JsonProperty("paternalSurname") String paternalSurname,
        @JsonProperty("maternalSurname") String maternalSurname,
        @JsonProperty("documentType") String documentType,
        @JsonProperty("documentNumber") String documentNumber,
        @JsonProperty("phone") String phone,
        @JsonProperty("email") String email,
        @JsonProperty("birthDate") LocalDate birthDate,
        @JsonProperty("relationship") String relationship,
        @JsonProperty("occupation") String occupation,
        @JsonProperty("address") String address
    ) {
        @com.fasterxml.jackson.annotation.JsonCreator
        public LegalResponsibleData(
            @JsonProperty("firstNames") String firstNames,
            @JsonProperty("paternalSurname") String paternalSurname,
            @JsonProperty("maternalSurname") String maternalSurname,
            @JsonProperty("documentType") String documentType,
            @JsonProperty("documentNumber") String documentNumber,
            @JsonProperty("phone") String phone,
            @JsonProperty("email") String email,
            @JsonProperty("birthDate") LocalDate birthDate,
            @JsonProperty("relationship") String relationship,
            @JsonProperty("occupation") String occupation,
            @JsonProperty("address") String address
        ) {
            this.firstNames = firstNames;
            this.paternalSurname = paternalSurname;
            this.maternalSurname = maternalSurname;
            this.documentType = documentType;
            this.documentNumber = documentNumber;
            this.phone = phone;
            this.email = email;
            this.birthDate = birthDate;
            this.relationship = relationship;
            this.occupation = occupation;
            this.address = address;
        }
    }
    
    public record TherapistData(
        @JsonProperty("firstNames") String firstNames,
        @JsonProperty("paternalSurname") String paternalSurname,
        @JsonProperty("maternalSurname") String maternalSurname,
        @JsonProperty("documentType") String documentType,
        @JsonProperty("documentNumber") String documentNumber,
        @JsonProperty("phone") String phone,
        @JsonProperty("email") String email,
        @JsonProperty("birthDate") LocalDate birthDate,
        @JsonProperty("licenseNumber") String licenseNumber,
        @JsonProperty("specialization") String specialization,
        @JsonProperty("university") String university,
        @JsonProperty("graduationDate") LocalDate graduationDate,
        @JsonProperty("certifications") List<String> certifications,
        @JsonProperty("address") String address,
        @JsonProperty("district") String district,
        @JsonProperty("province") String province,
        @JsonProperty("region") String region,
        @JsonProperty("country") String country
    ) {
        @com.fasterxml.jackson.annotation.JsonCreator
        public TherapistData(
            @JsonProperty("firstNames") String firstNames,
            @JsonProperty("paternalSurname") String paternalSurname,
            @JsonProperty("maternalSurname") String maternalSurname,
            @JsonProperty("documentType") String documentType,
            @JsonProperty("documentNumber") String documentNumber,
            @JsonProperty("phone") String phone,
            @JsonProperty("email") String email,
            @JsonProperty("birthDate") LocalDate birthDate,
            @JsonProperty("licenseNumber") String licenseNumber,
            @JsonProperty("specialization") String specialization,
            @JsonProperty("university") String university,
            @JsonProperty("graduationDate") LocalDate graduationDate,
            @JsonProperty("certifications") List<String> certifications,
            @JsonProperty("address") String address,
            @JsonProperty("district") String district,
            @JsonProperty("province") String province,
            @JsonProperty("region") String region,
            @JsonProperty("country") String country
        ) {
            this.firstNames = firstNames;
            this.paternalSurname = paternalSurname;
            this.maternalSurname = maternalSurname;
            this.documentType = documentType;
            this.documentNumber = documentNumber;
            this.phone = phone;
            this.email = email;
            this.birthDate = birthDate;
            this.licenseNumber = licenseNumber;
            this.specialization = specialization;
            this.university = university;
            this.graduationDate = graduationDate;
            this.certifications = certifications;
            this.address = address;
            this.district = district;
            this.province = province;
            this.region = region;
            this.country = country;
        }
    }
}



