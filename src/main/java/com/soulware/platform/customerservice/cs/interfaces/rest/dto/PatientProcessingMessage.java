package com.soulware.platform.customerservice.cs.interfaces.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record PatientProcessingMessage(
    @JsonProperty("messageId") String messageId,
    @JsonProperty("fileName") String fileName,
    @JsonProperty("uploadedAt") LocalDateTime uploadedAt,
    @JsonProperty("patientData") CompletePatientDataRequest patientData,
    @JsonProperty("retryCount") int retryCount,
    @JsonProperty("status") String status
) {
    @JsonCreator
    public PatientProcessingMessage(
        @JsonProperty("messageId") String messageId,
        @JsonProperty("fileName") String fileName,
        @JsonProperty("uploadedAt") LocalDateTime uploadedAt,
        @JsonProperty("patientData") CompletePatientDataRequest patientData,
        @JsonProperty("retryCount") int retryCount,
        @JsonProperty("status") String status
    ) {
        this.messageId = messageId;
        this.fileName = fileName;
        this.uploadedAt = uploadedAt;
        this.patientData = patientData;
        this.retryCount = retryCount;
        this.status = status;
    }
}