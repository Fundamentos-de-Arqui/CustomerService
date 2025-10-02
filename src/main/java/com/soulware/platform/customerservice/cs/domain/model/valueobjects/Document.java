package com.soulware.platform.customerservice.cs.domain.model.valueobjects;


import jakarta.persistence.Embeddable;


import java.util.Objects;
@Embeddable
public record Document(DocumentType type, String number) {
  public Document {
    Objects.requireNonNull(type, "type");
    if (number == null || number.isBlank()) throw new IllegalArgumentException("document number required");
  }
}
