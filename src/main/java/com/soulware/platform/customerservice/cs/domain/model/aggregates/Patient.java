package com.soulware.platform.customerservice.cs.domain.model.aggregates;

import com.soulware.platform.customerservice.cs.domain.model.commands.CreatePatientCommand;
import com.soulware.platform.customerservice.cs.domain.model.valueobjects.Document;
import com.soulware.platform.customerservice.cs.domain.model.valueobjects.PersonName;
import com.soulware.platform.customerservice.shared.model.aggregates.AuditableAbstractAggregateRoot;
import com.soulware.platform.customerservice.shared.model.entities.AuditableModel;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Patient extends AuditableAbstractAggregateRoot<Patient>{
    @Embedded
    private PersonName name;
    @Embedded
    private Document document;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false)
    private String receiptType;

    public Patient() {}

    public Patient(CreatePatientCommand command) {
        name = new PersonName(command.firstNames(), command.paternalSurname(), command.maternalSurname());
        this.document = new Document(command.type(), command.number());
        this.phone = command.phone();
        this.birthDate = command.birthDate();
        this.receiptType = command.receiptType();
    }


}
