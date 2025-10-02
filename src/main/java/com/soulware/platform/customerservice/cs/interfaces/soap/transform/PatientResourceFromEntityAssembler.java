package com.soulware.platform.customerservice.cs.interfaces.soap.transform;

import com.soulware.platform.customerservice.cs.domain.model.aggregates.Patient;
import com.soulware.platform.customerservice.cs.interfaces.soap.resources.PatientResource;

public final class PatientResourceFromEntityAssembler {
  private PatientResourceFromEntityAssembler() {}

  public static PatientResource toResource(Patient p) {
    return new PatientResource(
        p.getId(),
        p.getName().firstNames(),
        p.getName().paternalSurname(),
        p.getName().maternalSurname(),
        p.getDocument().type(),
        p.getDocument().number(),
        p.getPhone(),
        p.getBirthDate(),
        p.getReceiptType()
    );
  }
}
