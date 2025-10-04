package com.soulware.platform.customerservice.cs.interfaces.rest.mappers;

import com.soulware.platform.customerservice.cs.domain.model.aggregates.PatientProfile;
import com.soulware.platform.customerservice.cs.domain.model.commands.CreatePatientCommand;
import com.soulware.platform.customerservice.cs.interfaces.rest.dto.CreatePatientRequest;
import com.soulware.platform.customerservice.cs.interfaces.rest.dto.PatientResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between REST DTOs and domain objects
 */
@Component
public class PatientMapper {

    /**
     * Convert CreatePatientRequest to CreatePatientCommand
     */
    public CreatePatientCommand toCommand(CreatePatientRequest request) {
        return new CreatePatientCommand(
            request.firstNames(),
            request.paternalSurname(),
            request.maternalSurname(),
            request.documentType(),
            request.documentNumber(),
            request.phone(),
            request.birthDate(),
            request.receiptType(),
            null, // email
            null, // birthPlace
            null, // ageFirstAppointment
            null, // ageCurrent
            null, // gender
            null, // maritalStatus
            null, // religion
            null, // educationLevel
            null, // occupation
            null, // currentEducationalInstitution
            null, // currentAddress
            null, // district
            null, // province
            null, // region
            null, // country
            null, // medicalDiagnosis
            null, // problemIdentified
            null, // additionalNotes
            null, // businessName
            null, // holder
            null, // rucOrDni
            null  // billingAddress
        );
    }

    /**
     * Convert Patient entity to PatientResponse
     */
    public PatientResponse toResponse(PatientProfile patient) {
        return new PatientResponse(
            patient.getId(),
            patient.getPersonalInfo().firstNames(),
            patient.getPersonalInfo().paternalSurname(),
            patient.getPersonalInfo().maternalSurname(),
            patient.getPersonalInfo().documentType(),
            patient.getPersonalInfo().identityDocumentNumber(),
            patient.getPersonalInfo().phone(),
            patient.getPersonalInfo().birthDate(),
            patient.getBillingInfo().receiptType()
        );
    }
}
