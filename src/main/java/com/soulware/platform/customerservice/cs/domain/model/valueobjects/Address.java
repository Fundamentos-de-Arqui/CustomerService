package com.soulware.platform.customerservice.cs.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public record Address(
    String currentAddress,
    String district,
    String province,
    String region,
    String country
) {
    public Address {
        Objects.requireNonNull(currentAddress, "currentAddress cannot be null");
        Objects.requireNonNull(district, "district cannot be null");
    }
}



