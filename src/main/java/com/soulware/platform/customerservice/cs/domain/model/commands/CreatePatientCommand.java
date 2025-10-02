package com.soulware.platform.customerservice.cs.domain.model.commands;


import com.soulware.platform.customerservice.cs.domain.model.valueobjects.DocumentType;

import java.time.LocalDate;

public record CreatePatientCommand(String firstNames, String paternalSurname, String maternalSurname, DocumentType type, String number, String phone, LocalDate birthDate, String receiptType) {
}
