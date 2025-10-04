package com.soulware.platform.customerservice.cs.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public record BillingInfo(
    String receiptType,
    String businessName,
    String holder,
    String rucOrDni,
    String billingAddress
) {
    public BillingInfo {
        // Allow null values for optional billing information
    }
}



