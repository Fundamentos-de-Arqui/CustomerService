package com.soulware.platform.customerservice.cs.domain.model.valueobjects;

import jakarta.persistence.Embeddable;


// domain/model/enums/DocumentType.java
public enum DocumentType { 
    DNI, 
    PASSPORT, 
    RUC,
    CE, // Carné de Extranjería
    OTHER 
}
