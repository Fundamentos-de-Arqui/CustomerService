package com.soulware.platform.customerservice.cs.interfaces.rest.mappers;

import com.soulware.platform.customerservice.cs.domain.model.aggregates.TherapistProfile;
import com.soulware.platform.customerservice.cs.domain.model.commands.CreateTherapistCommand;
import com.soulware.platform.customerservice.cs.interfaces.rest.dto.CreateTherapistRequest;
import com.soulware.platform.customerservice.cs.interfaces.rest.dto.TherapistResponse;
import org.springframework.stereotype.Component;

@Component
public class TherapistMapper {

    public CreateTherapistCommand toCommand(CreateTherapistRequest request) {
        return new CreateTherapistCommand(
            request.firstNames(),
            request.paternalSurname(),
            request.maternalSurname(),
            request.documentType(),
            request.documentNumber(),
            request.phone(),
            request.email(),
            request.birthDate(),
            request.licenseNumber(),
            request.specialization(),
            request.university(),
            request.graduationDate(),
            request.certifications(),
            request.address(),
            request.district(),
            request.province(),
            request.region(),
            request.country()
        );
    }

    public TherapistResponse toResponse(TherapistProfile therapist) {
        return new TherapistResponse(
            therapist.getId(),
            therapist.getFirstNames(),
            therapist.getPaternalSurname(),
            therapist.getMaternalSurname(),
            therapist.getDocumentType().toString(),
            therapist.getIdentityDocumentNumber(),
            therapist.getPhone(),
            therapist.getEmail(),
            null, // birthDate not available in current entity
            therapist.getLicenseNumber(),
            therapist.getSpecialization(),
            therapist.getUniversity(),
            therapist.getGraduationDate(),
            therapist.getCertifications(),
            therapist.getAddress(),
            therapist.getDistrict(),
            therapist.getProvince(),
            therapist.getRegion(),
            therapist.getCountry()
        );
    }
}
