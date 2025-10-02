package com.soulware.platform.customerservice.cs.interfaces.soap.transform;

import com.soulware.platform.customerservice.cs.domain.model.commands.CreatePatientCommand;
import com.soulware.platform.customerservice.cs.interfaces.soap.resources.CreatePatientResource;

public final class CreatePatientCommandFromResourceAssembler {
  private CreatePatientCommandFromResourceAssembler() {}

  public static CreatePatientCommand toCommand(CreatePatientResource r) {
    return new CreatePatientCommand(
        r.firstNames(),
        r.paternalSurname(),
        r.maternalSurname(),
        r.documentType(),
        r.documentNumber(),
        r.phone(),
        r.birthDate(),
        r.receiptType()
    );
  }
}
