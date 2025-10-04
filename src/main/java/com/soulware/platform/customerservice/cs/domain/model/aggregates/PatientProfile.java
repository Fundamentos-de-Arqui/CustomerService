package com.soulware.platform.customerservice.cs.domain.model.aggregates;

import com.soulware.platform.customerservice.cs.domain.model.commands.CreatePatientCommand;
import com.soulware.platform.customerservice.cs.domain.model.valueobjects.*;
import com.soulware.platform.customerservice.shared.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patient_profiles")
@Getter
@Setter
public class PatientProfile extends AuditableAbstractAggregateRoot<PatientProfile> {
    
    
    @Embedded
    private PersonalInfo personalInfo;
    
    @Embedded
    private Address address;
    
    @Embedded
    private MedicalInfo medicalInfo;
    
    @Embedded
    private BillingInfo billingInfo;
    
    @OneToMany(mappedBy = "patientProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LegalResponsibleProfile> legalResponsibles = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "patient_therapist_assignments",
        joinColumns = @JoinColumn(name = "patient_profile_id"),
        inverseJoinColumns = @JoinColumn(name = "therapist_profile_id")
    )
    private List<TherapistProfile> assignedTherapists = new ArrayList<>();
    
    public PatientProfile() {}
    
    public PatientProfile(CreatePatientCommand command) {
        this.personalInfo = new PersonalInfo(
            command.firstNames(),
            command.paternalSurname(),
            command.maternalSurname(),
            command.documentNumber(),
            command.documentType(),
            command.phone(),
            null, // email - not in current command
            null, // birthPlace - not in current command
            command.birthDate(),
            null, // ageFirstAppointment - not in current command
            null, // ageCurrent - not in current command
            null, // gender - not in current command
            null, // maritalStatus - not in current command
            null, // religion - not in current command
            null, // educationLevel - not in current command
            null, // occupation - not in current command
            null  // currentEducationalInstitution - not in current command
        );
        
        this.billingInfo = new BillingInfo(
            command.receiptType(),
            null, // businessName - not in current command
            null, // holder - not in current command
            null, // rucOrDni - not in current command
            null  // billingAddress - not in current command
        );
    }
}
