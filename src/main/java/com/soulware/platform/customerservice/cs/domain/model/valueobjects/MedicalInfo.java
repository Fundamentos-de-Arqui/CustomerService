package com.soulware.platform.customerservice.cs.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public record MedicalInfo(
    String medicalDiagnosis,
    String problemIdentified,
    String additionalNotes
) {
    public MedicalInfo {
        // Allow null values for optional medical information
    }
}



