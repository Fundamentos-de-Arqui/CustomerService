package com.soulware.platform.customerservice.cs.interfaces.rest.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PatientProcessingResult(
    String messageId,
    String status,
    String message,
    LocalDateTime processedAt,
    PatientResponse patient,
    List<LegalResponsibleResponse> legalResponsibles,
    List<TherapistResponse> therapists
) {}