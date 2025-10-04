package com.soulware.platform.customerservice.cs.domain.model.aggregates;

import com.soulware.platform.customerservice.cs.domain.model.valueobjects.DocumentType;
import com.soulware.platform.customerservice.shared.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "therapist_profiles")
@Getter
@Setter
public class TherapistProfile extends AuditableAbstractAggregateRoot<TherapistProfile> {
    
    
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
    private String specialty;
    
    @Column(nullable = false)
    private String attentionPlace;
    
    private String licenseNumber;
    private String specialization;
    private String university;
    private java.time.LocalDate graduationDate;
    
    @ElementCollection
    @CollectionTable(name = "therapist_certifications", joinColumns = @JoinColumn(name = "therapist_id"))
    @Column(name = "certification")
    private List<String> certifications = new ArrayList<>();
    
    private String address;
    private String district;
    private String province;
    private String region;
    private String country;
    
    @ManyToMany(mappedBy = "assignedTherapists")
    private List<PatientProfile> assignedPatients = new ArrayList<>();
    
    public TherapistProfile() {}
    
    public TherapistProfile(String firstNames, String paternalSurname, String maternalSurname,
                          String identityDocumentNumber, DocumentType documentType, String phone,
                          String email, String specialty, String attentionPlace) {
        this.firstNames = firstNames;
        this.paternalSurname = paternalSurname;
        this.maternalSurname = maternalSurname;
        this.identityDocumentNumber = identityDocumentNumber;
        this.documentType = documentType;
        this.phone = phone;
        this.email = email;
        this.specialty = specialty;
        this.attentionPlace = attentionPlace;
    }
    
    public TherapistProfile(String firstNames, String paternalSurname, String maternalSurname,
                          String documentType, String documentNumber, String phone,
                          String email, java.time.LocalDate birthDate, String licenseNumber,
                          String specialization, String university, java.time.LocalDate graduationDate,
                          List<String> certifications, String address, String district,
                          String province, String region, String country) {
        this.firstNames = firstNames;
        this.paternalSurname = paternalSurname;
        this.maternalSurname = maternalSurname;
        this.identityDocumentNumber = documentNumber;
        this.documentType = DocumentType.valueOf(documentType);
        this.phone = phone;
        this.email = email;
        this.licenseNumber = licenseNumber;
        this.specialization = specialization;
        this.university = university;
        this.graduationDate = graduationDate;
        this.certifications = certifications != null ? certifications : new ArrayList<>();
        this.address = address;
        this.district = district;
        this.province = province;
        this.region = region;
        this.country = country;
    }
}
