package com.soulware.platform.customerservice.cs.application.internal.services;

import com.soulware.platform.customerservice.cs.domain.model.aggregates.LegalResponsibleProfile;
import com.soulware.platform.customerservice.cs.domain.model.aggregates.PatientProfile;
import com.soulware.platform.customerservice.cs.domain.model.aggregates.TherapistProfile;
import com.soulware.platform.customerservice.cs.domain.model.commands.CreateLegalResponsibleCommand;
import com.soulware.platform.customerservice.cs.domain.model.commands.CreatePatientCommand;
import com.soulware.platform.customerservice.cs.domain.model.commands.CreateTherapistCommand;
import com.soulware.platform.customerservice.cs.domain.model.valueobjects.DocumentType;
import com.soulware.platform.customerservice.cs.domain.services.LegalResponsibleCommandService;
import com.soulware.platform.customerservice.cs.domain.services.PatientCommandService;
import com.soulware.platform.customerservice.cs.domain.services.TherapistCommandService;
import com.soulware.platform.customerservice.cs.interfaces.rest.dto.CompletePatientDataRequest;
import com.soulware.platform.customerservice.cs.interfaces.rest.dto.ExcelProcessingResult;
import com.soulware.platform.customerservice.cs.interfaces.rest.dto.LegalResponsibleResponse;
import com.soulware.platform.customerservice.cs.interfaces.rest.dto.PatientResponse;
import com.soulware.platform.customerservice.cs.interfaces.rest.dto.TherapistResponse;
import com.soulware.platform.customerservice.cs.interfaces.rest.mappers.LegalResponsibleMapper;
import com.soulware.platform.customerservice.cs.interfaces.rest.mappers.PatientMapper;
import com.soulware.platform.customerservice.cs.interfaces.rest.mappers.TherapistMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompleteExcelProcessor {

    private final PatientCommandService patientCommandService;
    private final LegalResponsibleCommandService legalResponsibleCommandService;
    private final TherapistCommandService therapistCommandService;
    private final PatientMapper patientMapper;
    private final LegalResponsibleMapper legalResponsibleMapper;
    private final TherapistMapper therapistMapper;

    public CompleteExcelProcessor(PatientCommandService patientCommandService,
                                LegalResponsibleCommandService legalResponsibleCommandService,
                                TherapistCommandService therapistCommandService,
                                PatientMapper patientMapper,
                                LegalResponsibleMapper legalResponsibleMapper,
                                TherapistMapper therapistMapper) {
        this.patientCommandService = patientCommandService;
        this.legalResponsibleCommandService = legalResponsibleCommandService;
        this.therapistCommandService = therapistCommandService;
        this.patientMapper = patientMapper;
        this.legalResponsibleMapper = legalResponsibleMapper;
        this.therapistMapper = therapistMapper;
    }

    public ExcelProcessingResult processCompleteData(CompletePatientDataRequest request) {
        try {
            // 1. Create patient
            CreatePatientCommand patientCommand = new CreatePatientCommand(
                request.firstNames(),
                request.paternalSurname(),
                request.maternalSurname(),
                DocumentType.valueOf(request.documentType()),
                request.documentNumber(),
                request.phone(),
                request.birthDate(),
                request.receiptType(),
                request.email(),
                request.birthPlace(),
                request.ageFirstAppointment(),
                request.ageCurrent(),
                request.gender(),
                request.maritalStatus(),
                request.religion(),
                request.educationLevel(),
                request.occupation(),
                request.currentEducationalInstitution(),
                request.currentAddress(),
                request.district(),
                request.province(),
                request.region(),
                request.country(),
                request.medicalDiagnosis(),
                request.problemIdentified(),
                request.additionalNotes(),
                request.businessName(),
                request.holder(),
                request.rucOrDni(),
                request.billingAddress()
            );

            Optional<PatientProfile> patientResult = patientCommandService.handle(patientCommand);
            if (patientResult.isEmpty()) {
                return new ExcelProcessingResult(null, new ArrayList<>(), new ArrayList<>(), 
                    "Failed to create patient", false);
            }

            PatientProfile patient = patientResult.get();
            PatientResponse patientResponse = patientMapper.toResponse(patient);

            // 2. Create legal responsibles
            List<LegalResponsibleResponse> legalResponsibleResponses = new ArrayList<>();
            if (request.legalResponsibles() != null && !request.legalResponsibles().isEmpty()) {
                for (CompletePatientDataRequest.LegalResponsibleData lrData : request.legalResponsibles()) {
                    try {
                        // Validate required fields for legal responsible
                        if (isBlank(lrData.firstNames()) && isBlank(lrData.paternalSurname())) {
                            System.err.println("Skipping legal responsible: missing required name fields");
                            continue;
                        }
                        
                        CreateLegalResponsibleCommand lrCommand = new CreateLegalResponsibleCommand(
                            lrData.firstNames() != null ? lrData.firstNames() : "",
                            lrData.paternalSurname() != null ? lrData.paternalSurname() : "",
                            lrData.maternalSurname() != null ? lrData.maternalSurname() : "",
                            lrData.documentType() != null ? lrData.documentType() : "DNI",
                            lrData.documentNumber() != null ? lrData.documentNumber() : "",
                            lrData.phone() != null ? lrData.phone() : "",
                            lrData.email() != null ? lrData.email() : "",
                            lrData.birthDate(),
                            lrData.relationship() != null ? lrData.relationship() : "Responsable Legal",
                            lrData.occupation() != null ? lrData.occupation() : "",
                            lrData.address() != null ? lrData.address() : "",
                            patient.getId()
                        );

                        Optional<LegalResponsibleProfile> lrResult = legalResponsibleCommandService.handle(lrCommand);
                        if (lrResult.isPresent()) {
                            legalResponsibleResponses.add(legalResponsibleMapper.toResponse(lrResult.get()));
                        } else {
                            System.err.println("Failed to create legal responsible: " + lrData.firstNames() + " " + lrData.paternalSurname());
                        }
                    } catch (Exception e) {
                        System.err.println("Error creating legal responsible: " + e.getMessage());
                        // Continue processing other responsibles
                    }
                }
            }

            // 3. Create therapists
            List<TherapistResponse> therapistResponses = new ArrayList<>();
            if (request.therapists() != null) {
                for (CompletePatientDataRequest.TherapistData tData : request.therapists()) {
                    CreateTherapistCommand tCommand = new CreateTherapistCommand(
                        tData.firstNames(),
                        tData.paternalSurname(),
                        tData.maternalSurname(),
                        tData.documentType(),
                        tData.documentNumber(),
                        tData.phone(),
                        tData.email(),
                        tData.birthDate(),
                        tData.licenseNumber(),
                        tData.specialization(),
                        tData.university(),
                        tData.graduationDate(),
                        tData.certifications(),
                        tData.address(),
                        tData.district(),
                        tData.province(),
                        tData.region(),
                        tData.country()
                    );

                    Optional<TherapistProfile> tResult = therapistCommandService.handle(tCommand);
                    if (tResult.isPresent()) {
                        therapistResponses.add(therapistMapper.toResponse(tResult.get()));
                    }
                }
            }

            return new ExcelProcessingResult(
                patientResponse,
                legalResponsibleResponses,
                therapistResponses,
                "Data processed successfully",
                true
            );

        } catch (Exception e) {
            return new ExcelProcessingResult(
                null,
                new ArrayList<>(),
                new ArrayList<>(),
                "Error processing data: " + e.getMessage(),
                false
            );
        }
    }
    
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
