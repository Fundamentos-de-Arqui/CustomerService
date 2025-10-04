package com.soulware.platform.customerservice.cs.interfaces.rest.dto;

import java.util.List;

public record ExcelProcessingResult(
    PatientResponse patient,
    List<LegalResponsibleResponse> legalResponsibles,
    List<TherapistResponse> therapists,
    String message,
    boolean success
) {}



