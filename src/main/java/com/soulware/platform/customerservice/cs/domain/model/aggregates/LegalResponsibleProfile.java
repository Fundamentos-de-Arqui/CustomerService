package com.soulware.platform.customerservice.cs.domain.model.aggregates;

import com.soulware.platform.customerservice.cs.domain.model.valueobjects.DocumentType;
import com.soulware.platform.customerservice.shared.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "legal_responsible_profiles")
@Getter
@Setter
public class LegalResponsibleProfile extends AuditableAbstractAggregateRoot<LegalResponsibleProfile> {
    
    
    @Column(nullable = false)
    private String firstNames;
    
    @Column(nullable = false)
    private String paternalSurname;
    
    private String maternalSurname;
    
    @Column(nullable = false)
    private String identityDocumentNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType documentType;
    
    private String phone;
    
    private String email;
    
    @Column(nullable = false)
    private String relationship;
    
    private String occupation;
    
    private String address;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_profile_id")
    private PatientProfile patientProfile;
    
    public LegalResponsibleProfile() {}
    
    public LegalResponsibleProfile(String firstNames, String paternalSurname, String maternalSurname,
                                 String identityDocumentNumber, DocumentType documentType, String phone,
                                 String email, String relationship) {
        this.firstNames = firstNames;
        this.paternalSurname = paternalSurname;
        this.maternalSurname = maternalSurname;
        this.identityDocumentNumber = identityDocumentNumber;
        this.documentType = documentType;
        this.phone = phone;
        this.email = email;
        this.relationship = relationship;
    }
    
    public LegalResponsibleProfile(String firstNames, String paternalSurname, String maternalSurname,
                                 String documentType, String documentNumber, String phone,
                                 String email, java.time.LocalDate birthDate, String relationship,
                                 String occupation, String address, PatientProfile patientProfile) {
        this.firstNames = firstNames;
        this.paternalSurname = paternalSurname;
        this.maternalSurname = maternalSurname;
        this.identityDocumentNumber = documentNumber;
        this.documentType = DocumentType.valueOf(documentType);
        this.phone = phone;
        this.email = email;
        this.relationship = relationship;
        this.occupation = occupation;
        this.address = address;
        this.patientProfile = patientProfile;
    }
}
