package com.soulware.platform.customerservice.cs.domain.model.aggregates;

import com.soulware.platform.customerservice.shared.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Auditable;

import java.time.LocalDate;
@Entity
@Getter
@Setter
public class FiliationFile extends AuditableAbstractAggregateRoot<FiliationFile> {
    private Long patientId;

    public FiliationFile() {}



}
