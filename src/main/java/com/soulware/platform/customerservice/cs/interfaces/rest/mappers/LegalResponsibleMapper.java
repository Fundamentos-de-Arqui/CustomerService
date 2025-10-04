package com.soulware.platform.customerservice.cs.interfaces.rest.mappers;

import com.soulware.platform.customerservice.cs.domain.model.aggregates.LegalResponsibleProfile;
import com.soulware.platform.customerservice.cs.domain.model.commands.CreateLegalResponsibleCommand;
import com.soulware.platform.customerservice.cs.interfaces.rest.dto.CreateLegalResponsibleRequest;
import com.soulware.platform.customerservice.cs.interfaces.rest.dto.LegalResponsibleResponse;
import org.springframework.stereotype.Component;

@Component
public class LegalResponsibleMapper {

    public CreateLegalResponsibleCommand toCommand(CreateLegalResponsibleRequest request) {
        return new CreateLegalResponsibleCommand(
            request.firstNames(),
            request.paternalSurname(),
            request.maternalSurname(),
            request.documentType(),
            request.documentNumber(),
            request.phone(),
            request.email(),
            request.birthDate(),
            request.relationship(),
            request.occupation(),
            request.address(),
            request.patientProfileId()
        );
    }

    public LegalResponsibleResponse toResponse(LegalResponsibleProfile legalResponsible) {
        return new LegalResponsibleResponse(
            legalResponsible.getId(),
            legalResponsible.getFirstNames(),
            legalResponsible.getPaternalSurname(),
            legalResponsible.getMaternalSurname(),
            legalResponsible.getDocumentType().toString(),
            legalResponsible.getIdentityDocumentNumber(),
            legalResponsible.getPhone(),
            legalResponsible.getEmail(),
            null, // birthDate not available in current entity
            legalResponsible.getRelationship(),
            legalResponsible.getOccupation(),
            legalResponsible.getAddress(),
            legalResponsible.getPatientProfile() != null ? legalResponsible.getPatientProfile().getId() : null
        );
    }
}
