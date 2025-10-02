package com.soulware.platform.customerservice.cs.interfaces.soap.endpoints;

import com.soulware.platform.customerservice.cs.application.internal.services.ExcelPatientFormParser;
import com.soulware.platform.customerservice.cs.domain.model.queries.GetAllPatientsQuery;
import com.soulware.platform.customerservice.cs.domain.services.PatientCommandService;
import com.soulware.platform.customerservice.cs.domain.services.PatientQueryService;
import com.soulware.platform.customerservice.cs.interfaces.soap.resources.CreatePatientResource;
import com.soulware.platform.customerservice.cs.interfaces.soap.resources.PatientResource;
import com.soulware.platform.customerservice.cs.interfaces.soap.transform.CreatePatientCommandFromResourceAssembler;
import com.soulware.platform.customerservice.cs.interfaces.soap.transform.PatientResourceFromEntityAssembler;
import com.soulware.platform.customerservice.ws.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeFactory;
import java.time.ZoneOffset;
import java.util.GregorianCalendar;

@Endpoint
public class PatientEndpointController {

    // Must match targetNamespace in src/main/resources/wsdl/patient.xsd
    private static final String NS = "http://soulware.com/customerservice/patient";

    private final PatientCommandService commands;
    private final PatientQueryService queries;
    private final ExcelPatientFormParser formParser;

    public PatientEndpointController(PatientCommandService commands, PatientQueryService queries, ExcelPatientFormParser formParser) {
        this.commands = commands;
        this.queries = queries;
        this.formParser = formParser;
    }

    // -------- CreatePatient --------
    @PayloadRoot(namespace = NS, localPart = "CreatePatientRequest")
    @ResponsePayload
    public CreatePatientResponse create(@RequestPayload CreatePatientRequest req) {
        // JAXB -> Resource -> Command -> Domain
        CreatePatientResource in = toResource(req);
        var created = commands.handle(CreatePatientCommandFromResourceAssembler.toCommand(in))
                .orElseThrow(); // swap for SOAP fault mapping if you prefer

        // Domain -> Resource -> JAXB
        PatientResource out = PatientResourceFromEntityAssembler.toResource(created);
        var resp = new CreatePatientResponse();
        resp.setPatient(toSoap(out));
        return resp;
    }

    // -------- GetAllPatients --------
    @PayloadRoot(namespace = NS, localPart = "GetAllPatientsRequest")
    @ResponsePayload
    public GetAllPatientsResponse getAll(@RequestPayload GetAllPatientsRequest req) {
        var resp = new GetAllPatientsResponse();
        queries.handle(new GetAllPatientsQuery()).stream()
                .map(PatientResourceFromEntityAssembler::toResource) // Domain -> Resource
                .map(this::toSoap)                                   // Resource -> JAXB
                .forEach(resp.getPatients()::add);
        return resp;
    }

    // ---------- helpers (tiny adapters around your records) ----------

    /** Map JAXB CreatePatientRequest -> your CreatePatientResource. */
    private CreatePatientResource toResource(CreatePatientRequest r) {
        var birth = r.getBirthDate().toGregorianCalendar().toZonedDateTime().toLocalDate();

        // Map XSD enums to your domain enums (adjust package if you placed enums elsewhere)
        var docType = com.soulware.platform.customerservice.cs.domain.model.valueobjects.DocumentType
                .valueOf(r.getDocumentType().value());


        return new CreatePatientResource(
                r.getFirstNames(),
                r.getPaternalSurname(),
                r.getMaternalSurname(),
                docType,
                r.getDocumentNumber(),
                r.getPhone(),
                birth,
                r.getReceiptType()
        );
    }

    /** Map your PatientResource -> JAXB Patient (generated from XSD). */
    private com.soulware.platform.customerservice.ws.Patient toSoap(PatientResource p) {
        try {
            var soap = new com.soulware.platform.customerservice.ws.Patient();
            soap.setId(p.id());
            soap.setFirstNames(p.firstNames());
            soap.setPaternalSurname(p.paternalSurname());
            soap.setMaternalSurname(p.maternalSurname());
            soap.setDocumentType(
                    com.soulware.platform.customerservice.ws.DocumentType.fromValue(p.documentType().name())
            );
            soap.setDocumentNumber(p.documentNumber());
            soap.setPhone(p.phone());

            var cal = GregorianCalendar.from(p.birthDate().atStartOfDay(ZoneOffset.UTC));
            soap.setBirthDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));

            soap.setReceiptType(p.receiptType());
            return soap;
        } catch (Exception e) {
            throw new RuntimeException("Failed to map PatientResource -> SOAP", e);
        }
    }

    // ===== Upload Excel form -> Create patient =====
    @PayloadRoot(namespace = NS, localPart = "UploadPatientFormRequest")
    @ResponsePayload
    public UploadPatientFormResponse upload(@RequestPayload UploadPatientFormRequest req) {
        var bytes = req.getContent(); // JAXB already decoded base64 → byte[]
        var resource = formParser.parse(new java.io.ByteArrayInputStream(bytes));

        var created = commands.handle(
                CreatePatientCommandFromResourceAssembler.toCommand(resource)
        ).orElseThrow();

        var out = PatientResourceFromEntityAssembler.toResource(created);
        var resp = new UploadPatientFormResponse();
        resp.setPatient(toSoap(out)); // reuse your mapper
        return resp;
    }

}
