package com.soulware.platform.customerservice.cs.interfaces.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

/**
 * Request DTO for uploading patient form via Excel file
 */
public record UploadPatientFormRequest(
    @NotBlank(message = "File name is required")
    String fileName,
    
    @NotNull(message = "File content is required")
    MultipartFile file
) {}


