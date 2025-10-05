package com.soulware.platform.customerservice.cs.interfaces.rest.dto;

import java.time.LocalDate;
import java.util.List;

public record TherapistResponse(
    Long id,
    String firstNames,
    String paternalSurname,
    String maternalSurname,
    String documentType,
    String documentNumber,
    String phone,
    String email,
    LocalDate birthDate,
    String licenseNumber,
    String specialization,
    String university,
    LocalDate graduationDate,
    List<String> certifications,
    String address,
    String district,
    String province,
    String region,
    String country
) {}





