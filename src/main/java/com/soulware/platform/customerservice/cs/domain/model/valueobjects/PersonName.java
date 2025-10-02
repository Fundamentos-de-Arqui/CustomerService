package com.soulware.platform.customerservice.cs.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record PersonName(String firstNames, String paternalSurname, String maternalSurname) {
    public PersonName{
        if (firstNames == null || firstNames.isBlank()) throw new IllegalArgumentException("firstNames required");
        if (paternalSurname == null || paternalSurname.isBlank()) throw new IllegalArgumentException("paternalSurname required");

    }
}
